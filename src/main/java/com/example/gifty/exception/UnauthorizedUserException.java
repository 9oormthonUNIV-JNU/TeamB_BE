package com.example.gifty.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnauthorizedUserException extends RuntimeException {
    private final ErrorCode errorCode;
}
