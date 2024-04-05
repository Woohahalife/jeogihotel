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
    DELETE_SUCCESS(false, BAD_REQUEST.value(),"삭제에 성공했습니다."),

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
    INVALID_DATE_SETTING(false, BAD_REQUEST.value(), "입실 & 퇴실일자 설정이 유효하지 않습니다."),
    EMAIL_IS_NOT_VALIDATE(false, BAD_REQUEST.value(), "해당 접근이 올바르지 않습니다."),
    ACCOMMODATION_DOES_NOT_EXIST(false, BAD_REQUEST.value(), "해당 숙소가 존재하지 않습니다."),
    DUPLICATE_DISCOUNTRATE(false, BAD_REQUEST.value(), "할인율이 중복입니다"),
    DISCOUNT_NOT_FOUND(false, BAD_REQUEST.value(), "할인율 정보를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(false, BAD_REQUEST.value(), "객실 정보를 찾을 수 없습니다."),
    NO_ROOMS_REMAINING(false, BAD_REQUEST.value(), "잔여 객실이 없습니다."),
    LOCATION_NOT_FOUND(false, BAD_REQUEST.value(), "지역 정보를 찾을 수 없습니다."),
    ERROR_SETTING_NUMBER_OF_GUEST(false, BAD_REQUEST.value(), "객실 인원 수 설정이 올바르지 않습니다."),
    SET_REQUIRED_INFORMATION(false, BAD_REQUEST.value(), "생성에 필요한 필수 정보가 누락되었습니다."),
    ERROR_SETTING_NUM_OF_PEOPLE(false, BAD_REQUEST.value(), "설정된 인원 수를 다시 확인해주세요"),
    DUPLICATE_LOCATION(false, BAD_REQUEST.value(), "지역 데이터 중복입니다."),
    RATE_OUT_OF_BOUND_OVER(false, BAD_REQUEST.value(), "평가는 5점을 넘을 수 없습니다."),
    RATE_OUT_OF_BOUND_UNDER(false, BAD_REQUEST.value(), "평가는 0점을 넘어야 합니다."),
    FAILURE_PRICING_POLICY(false, BAD_REQUEST.value(), "객실의 최소 가격은 30000원 이상이어야 합니다."),
    DELETE_FAIL(false, BAD_REQUEST.value(),"삭제에 실패했습니다."),
    IMAGE_NOT_FOUND(false, BAD_REQUEST.value(), "이미지 경로가 존재하지 않습니다."),
    DUPLICATED_IMAGE(false, BAD_REQUEST.value(), "중복된 이미지 경로입니다."),

    TYPE_MISMATCH(false, BAD_REQUEST.value(), "데이터 타입이 맞지 않습니다."),
    JSON_PARSE_ERROR(false, BAD_REQUEST.value(), "유효한 JSON 데이터가 아닙니다. 전달하는 데이터 타입을 확인해주세요."),
    RESERVAION_IS_DUPLICATE(false, BAD_REQUEST.value(), "해당 객실의 예약 내역이 이미 존재합니다."),

    EXPIRED_ACCESSTOKEN(false, UNAUTHORIZED.value(), "토큰이 만료되었습니다."),

    /**
     * 500
     */
    INVALID_ERROR(false, INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;
}
