package com.example.gifty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(NoSuchElementFoundException.class)
//    public ResponseEntity<?> handleNoSuchElementFoundException(NoSuchElementFoundException exception) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .code("Item Not Found")
//                .message(exception.getMessage()).build();
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//    }
}
