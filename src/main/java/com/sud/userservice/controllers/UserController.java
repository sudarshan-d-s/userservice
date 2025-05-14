package com.sud.userservice.controllers;


import com.sud.userservice.dtos.*;
import com.sud.userservice.models.Token;
import com.sud.userservice.models.User;
import com.sud.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        SignUpResponseDto user = SignUpResponseDto.fromUser(userService.signUp(signUpRequestDto.getName(),
                signUpRequestDto.getEmail(), signUpRequestDto.getPassword()));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        Token token = userService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        return new ResponseEntity<>(new LoginResponseDto(token.getValue()), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(Map.of("Status", "Successfully logged out!"),
                HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<UserDto> verifyToken(@RequestHeader("Authorization") String token){
        User user = userService.validate(token);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

}
