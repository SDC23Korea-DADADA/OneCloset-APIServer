package com.dadada.onecloset.exception;

import com.dadada.onecloset.global.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse customException(CustomException e) {
        log.debug("message : {}", e.getMessage());
        return new CommonResponse(e.getExceptionType().getCode(), e.getExceptionType().getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse globalExceptionHandler(Exception e) {
        log.error("global exception occur : {}", e.getMessage());
        return new CommonResponse(500, "서버 에러");
    }

}
