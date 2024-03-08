package com.example.gifty.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaopayFailException extends RuntimeException {
    private final ErrorCode errorCode;
}
