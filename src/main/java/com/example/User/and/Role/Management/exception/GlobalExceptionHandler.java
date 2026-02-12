package com.example.User.and.Role.Management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.User.and.Role.Management.dto.response.ApiError;
import org.springframework.security.access.AccessDeniedException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException exp){
        return new ResponseEntity<>(
                new ApiError(exp.getMessage(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiError> hadnleRoleNotFound(RoleNotFoundException exp){
        return new ResponseEntity<>(
                new ApiError( exp.getMessage(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserRoleAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserRoleAlreadyExists(UserRoleAlreadyExistsException ex) {
        return new ResponseEntity<>(
                new ApiError(ex.getMessage(), HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT
        );
    }



//    These are built-in Spring Security exceptions:
//      BadCredentialsException
//      AccessDeniedException
//    They already exist in Spring Security JARs

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials() {
        return new ResponseEntity<>(
                new ApiError("Invalid username or password", HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied() {
        return new ResponseEntity<>(
                new ApiError("You are not allowed to access this resource", HttpStatus.FORBIDDEN.value()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                new ApiError("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
