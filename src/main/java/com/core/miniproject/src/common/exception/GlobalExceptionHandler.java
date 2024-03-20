package com.core.miniproject.src.common.exception;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<T>> baseExceptionHandler(BaseException e) {
        log.warn("[AppException Occurs] message: {} HttpStatusCode: {}", e.getStatus().getMessage(),
                e.getStatus().getStatusCode());

        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(BaseResponse.response(e.getStatus()));
    }

    @ExceptionHandler(Exception.class) // 아직 규정하지 못한 예외에 한해 작동
    public ResponseEntity<BaseResponse<T>> exceptionHandler(Exception e) {
        log.error("[InternalServerError Occurs] error: {}", e.getMessage());
        e.printStackTrace();

        return ResponseEntity.internalServerError()
                .body(BaseResponse.response(BaseResponseStatus.INVALID_ERROR));
    }
}
