package com.example.gifty.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidJWTTokenException extends RuntimeException {
    private final ErrorCode errorCode;
}
