package com.example.User.and.Role.Management.exception;

public class UserRoleAlreadyExistsException extends RuntimeException{
    public UserRoleAlreadyExistsException(String msg){
        super(msg);
    }
}
