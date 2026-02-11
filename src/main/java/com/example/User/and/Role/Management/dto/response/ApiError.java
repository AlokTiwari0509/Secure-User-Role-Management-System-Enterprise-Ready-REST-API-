package com.example.User.and.Role.Management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String message;
    private int status;

    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
