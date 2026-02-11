package com.example.User.and.Role.Management.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String msg){
        super(msg);
    }
}
