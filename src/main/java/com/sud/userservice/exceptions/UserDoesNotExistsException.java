package com.sud.userservice.exceptions;

public class UserDoesNotExistsException extends Exception{

    public UserDoesNotExistsException(String message){
        super(message);
    }

}
