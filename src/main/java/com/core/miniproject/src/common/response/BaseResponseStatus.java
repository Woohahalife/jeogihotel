package com.core.miniproject.src.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /**
     * 200(OK)
     */
    SUCCESS(true, OK.value(), "요청에 성공했습니다."),

    /**
     * 400
     */


    /**
     * 500
     */
    INVALID_ERROR(false, INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;
}
