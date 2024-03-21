package com.core.miniproject.src.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    EMAIL_NOT_FOUND(false, BAD_REQUEST.value(), "해당 이메일이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(false, BAD_REQUEST.value(), "해당 회원을 찾을 수 없습니다."),
    NOT_BEARER_TOKEN(false, BAD_REQUEST.value(), "토큰이 BEARER로 시작하지 않거나 null입니다."),
    NOT_AUTHENTICATED_USER(false, BAD_REQUEST.value(), "인증된 사용자가 아닙니다."),
    PRINCIPAL_IS_NOT_FOUND(false, BAD_REQUEST.value(), "사용자 인증 주체 정보가 존재하지 않습니다."),
    DUPLICAE_EMAIL(false, BAD_REQUEST.value(), "가입된 이메일이 이미 존재합니다."),
    INVALID_PASSWORD(false, BAD_REQUEST.value(), "아이디나 비밀번호가 일지하지 않습니다."),
    /**
     * 500
     */
    INVALID_ERROR(false, INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;
}
