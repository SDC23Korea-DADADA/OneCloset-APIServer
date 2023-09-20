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
    JWT_TOKEN_EXPIRED(4001, "토큰이 만료되었습니다."),
    JWT_TOKEN_PARSE_ERROR(4002, "JWT 토큰 파싱에 실패하였습니다."),
    AUTHENTICATION_FAILED(4003, "유저 인증에 실패하였습니다."),

    CLOSET_NOT_FOUND(4001, "존재하지 않는 옷장입니다."),
    CLOTHES_NOT_FOUND(4002, "존재하지 않는 의류입니다."),

    COLOR_NOT_FOUND(4003, "등록할 수 없는 색상입니다."),
    TYPE_NOT_FOUND(4004, "등록할 수 없는 종류입니다."),
    MATERIAL_NOT_FOUND(4005, "등록할 수 없는 재질입니다."),

    MODEL_NOT_FOUND(4006, "존재하지 않는 모델 입니다."),
    FITTING_NOT_FOUND(4007, "존재하지 않는 피팅 정보입니다."),
    CARE_SOLUTION_NOT_FOUND(4008, "세탁정보가 존재하지 않습니다.");

    private final int code;
    private final String message;

}
