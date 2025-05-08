package com.sud.userservice.services;

import com.sud.userservice.models.Token;
import com.sud.userservice.models.User;

public interface UserService {

    User signUp(String name, String email, String hashedPassword);

    Token login(String email, String hashedPassword);

    void logout();

    User validate(String token);

}
