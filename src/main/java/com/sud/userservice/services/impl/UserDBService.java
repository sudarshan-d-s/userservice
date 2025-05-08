package com.sud.userservice.services.impl;

import com.sud.userservice.models.Token;
import com.sud.userservice.models.User;
import com.sud.userservice.services.UserService;

public class UserDBService implements UserService {
    @Override
    public User signUp(String name, String email, String hashedPassword) {
        return null;
    }

    @Override
    public Token login(String email, String hashedPassword) {
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public User validate(String token) {
        return null;
    }
}
