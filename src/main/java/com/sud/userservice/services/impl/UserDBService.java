package com.sud.userservice.services.impl;

import com.sud.userservice.models.Token;
import com.sud.userservice.models.User;
import com.sud.userservice.repositories.RoleRepository;
import com.sud.userservice.repositories.TokenRepository;
import com.sud.userservice.repositories.UserRepository;
import com.sud.userservice.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserDBService implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDBService(UserRepository userRepository, TokenRepository tokenRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User signUp(String name, String email, String password) {

        Optional<User> usr = userRepository.findByEmail(email);

        if(usr.isPresent()){
            //TODO throw new UserAlreadyExistsException();
            return null;
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
            //TODO throw new UserDoesNotExistsException();
            return null;
        }
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            //TODO throw new IncorrectPasswordException();
            return null;
        }

        Token token = createToken(user);
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);
        token.setExpiresAt(expiryDate);
        token.setIsDeleted(false);

        return tokenRepository.save(token);

    }

    @Override
    public void logout() {

    }

    @Override
    public User validate(String token) {
        return null;
    }

    private Token createToken(User user){
        Token token = new Token();
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);
        return token;
    }

}
