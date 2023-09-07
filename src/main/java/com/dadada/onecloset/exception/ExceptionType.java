package com.dadada.onecloset.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionType {

    /**
     * code : 예외 코드 (4자리, 맨앞자리는 HTTP 상태코드를 따라감)
     * message : 예외 메시지
     */

    USER_NOT_FOUND(4000, "유저가 존재하지 않습니다."),
    CLOSET_NOT_FOUND(4001, "옷장이 존재하지 않습니다.");

    private final int code;
    private final String message;

}
