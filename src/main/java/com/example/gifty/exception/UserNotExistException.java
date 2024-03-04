package com.example.gifty.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNotExistException extends RuntimeException {
    private final ErrorCode errorCode;
}
