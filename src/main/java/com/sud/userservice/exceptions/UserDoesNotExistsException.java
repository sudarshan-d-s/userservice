package com.sud.userservice.exceptions;

public class UserDoesNotExistsException extends RuntimeException{

    public UserDoesNotExistsException(String message){
        super(message);
    }

}
