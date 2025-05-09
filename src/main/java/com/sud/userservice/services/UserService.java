package com.sud.userservice.services;

import com.sud.userservice.exceptions.IncorrectPasswordException;
import com.sud.userservice.exceptions.UserAlreadyExistsException;
import com.sud.userservice.exceptions.UserDoesNotExistsException;
import com.sud.userservice.models.Token;
import com.sud.userservice.models.User;
import com.sud.userservice.exceptions.TokenInvalidException;

public interface UserService {

    User signUp(String name, String email, String hashedPassword) throws UserAlreadyExistsException;

    Token login(String email, String hashedPassword) throws UserDoesNotExistsException, IncorrectPasswordException;

    public User logout(String tokenStr) throws TokenInvalidException;

    User validate(String token) throws TokenInvalidException;

}
