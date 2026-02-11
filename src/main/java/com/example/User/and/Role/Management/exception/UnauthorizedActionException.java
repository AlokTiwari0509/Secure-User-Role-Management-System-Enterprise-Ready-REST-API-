package com.example.User.and.Role.Management.exception;

public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException(String msg){
        super(msg);
    }
}
