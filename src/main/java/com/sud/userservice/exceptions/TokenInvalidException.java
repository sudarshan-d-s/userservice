package com.sud.userservice.exceptions;

public class TokenInvalidException extends RuntimeException{

    public TokenInvalidException(String message){
        super(message);
    }

}
