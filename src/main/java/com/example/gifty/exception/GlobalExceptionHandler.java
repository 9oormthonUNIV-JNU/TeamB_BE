package com.example.gifty.exception;

import com.example.gifty.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJWTTokenException.class)
    public ResponseEntity<?> handleUserNotExistException(InvalidJWTTokenException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<?> handleUserNotExistException(UserNotExistException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(ProductNotExistException.class)
    public ResponseEntity<?> handleUserNotExistException(ProductNotExistException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ApiResponse.error(e.getMessage()));
    }
}
