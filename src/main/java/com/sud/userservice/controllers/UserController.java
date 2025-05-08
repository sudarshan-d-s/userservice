package com.sud.userservice.controllers;


import com.sud.userservice.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){

        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto logoutRequestDto){

        return null;
    }

    @PostMapping("/validate")
    public ResponseEntity<UserDto> verifyToken(@RequestHeader("Authorization") String token){

        return null;
    }


}
