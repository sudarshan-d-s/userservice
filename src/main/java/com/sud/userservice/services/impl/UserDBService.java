package com.sud.userservice.services.impl;

import com.sud.userservice.exceptions.IncorrectPasswordException;
import com.sud.userservice.exceptions.UserAlreadyExistsException;
import com.sud.userservice.exceptions.UserDoesNotExistsException;
import com.sud.userservice.models.Token;
import com.sud.userservice.models.User;
import com.sud.userservice.repositories.RoleRepository;
import com.sud.userservice.repositories.TokenRepository;
import com.sud.userservice.repositories.UserRepository;
import com.sud.userservice.services.UserService;
import com.sud.userservice.exceptions.TokenInvalidException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserDBService implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDBService(UserRepository userRepository, TokenRepository tokenRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User signUp(String name, String email, String password) {

        Optional<User> usr = userRepository.findByEmail(email);

        if(usr.isPresent()){
            throw new UserAlreadyExistsException("The email "+email+" is already in use!");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            throw new UserDoesNotExistsException("The email ["+email+"] is not registered!");
        }
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            throw new IncorrectPasswordException("Incorrect password!");
        }

        Token token = createToken(user);
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);
        token.setExpiresAt(expiryDate);
        token.setIsDeleted(false);

        return tokenRepository.save(token);

    }

    @Override
    public void logout(String tokenStr) {

        Optional<Token> tokenOptional = tokenRepository
                .findByValueAndIsDeletedAndExpiresAtGreaterThan(tokenStr, false, LocalDateTime.now());

        Token token = tokenOptional.orElseThrow(() ->{
            return new TokenInvalidException("Token ["+tokenStr+"] is invalid!");
        });

        token.setIsDeleted(true);
        tokenRepository.save(token);
    }

    @Override
    public User validate(String tokenStr) {

        Optional<Token> userOptional = tokenRepository
                .findByValueAndIsDeletedAndExpiresAtGreaterThan(tokenStr, false, LocalDateTime.now());

        return userOptional.map(Token::getUser).orElseThrow(() ->{
            return new TokenInvalidException("Token ["+tokenStr+"] is invalid!");
        });
    }

    private Token createToken(User user){
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        LocalDateTime localDateTime = LocalDateTime.now();
        token.setExpiresAt(localDateTime.plusDays(30));

        token.setIsDeleted(false);
        token.setUser(user);

        return token;
    }

}
