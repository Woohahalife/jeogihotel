package com.core.miniproject.src.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.core.miniproject.src.common.response.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess, statusCode, message, status, data"})
public class BaseResponse<T> {

    private Boolean isSuccess;
    private int statusCode;
    private String message;
    private BaseResponseStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 성공 응답 모델
    public static <T> BaseResponse<T> response(T data) {
        return new BaseResponse<>(
                SUCCESS.isSuccess(),
                SUCCESS.getStatusCode(),
                SUCCESS.getMessage(),
                SUCCESS,
                data
        );
    }

    // 실패 응답 모델 - 에러 관련 응답이 반환되기 때문에 data는 null
    public static <T> BaseResponse<T> response(BaseResponseStatus status) {
        return new BaseResponse<>(
                status.isSuccess(),
                status.getStatusCode(),
                status.getMessage(),
                status,
                null
        );
    }
}
