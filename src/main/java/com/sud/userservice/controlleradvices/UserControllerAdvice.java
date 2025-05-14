package com.sud.userservice.controlleradvices;

import com.sud.userservice.dtos.ErrorDto;
import com.sud.userservice.exceptions.IncorrectPasswordException;
import com.sud.userservice.exceptions.TokenInvalidException;
import com.sud.userservice.exceptions.UserAlreadyExistsException;
import com.sud.userservice.exceptions.UserDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> incorrectPassWordHandler(IncorrectPasswordException exp){
        return new ResponseEntity<>(new ErrorDto(exp.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<?> tokenInvalidHandler(TokenInvalidException exp){
        return new ResponseEntity<>(new ErrorDto(exp.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> userAlreadyExistsExceptionHandler(UserAlreadyExistsException exp){
        return new ResponseEntity<>(new ErrorDto(exp.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<?> userDoesNotExistsExceptionHandler(UserDoesNotExistsException exp){
        return new ResponseEntity<>(new ErrorDto(exp.getMessage()),
                HttpStatus.NOT_FOUND);
    }

}
