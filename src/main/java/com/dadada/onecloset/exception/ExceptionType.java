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

    USER_NOT_FOUND(4000, "존재하지 않는 유저입니다."),
    CLOSET_NOT_FOUND(4001, "존재하지 않는 옷장입니다."),
    CLOTHES_NOT_FOUND(4002, "존재하지 않는 의류입니다."),

    COLOR_NOT_FOUND(4003, "등록할 수 없는 색상입니다."),
    TYPE_NOT_FOUND(4004, "등록할 수 없는 종류입니다."),
    MATERIAL_NOT_FOUND(4005, "등록할 수 없는 재질입니다.");

    private final int code;
    private final String message;

}
