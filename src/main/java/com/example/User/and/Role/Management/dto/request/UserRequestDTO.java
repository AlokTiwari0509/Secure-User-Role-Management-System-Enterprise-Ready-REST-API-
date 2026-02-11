package com.example.User.and.Role.Management.dto.request;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private String status;
}
