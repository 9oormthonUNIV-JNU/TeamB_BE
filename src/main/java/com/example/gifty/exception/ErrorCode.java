package com.example.gifty.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 Bad Request 잘못된 요청

    // 401 Unauthorized 권한 없음

    // 403 Forbidden 금지됨

    // 404 Not Found 찾을 수 없음

    // 409 Conflict 충돌

    // 410 Gone 사라짐

    // 500 Internal Server Error 내부 서버 오류

    // 501 Not Implemented 구현되지 않음
}
