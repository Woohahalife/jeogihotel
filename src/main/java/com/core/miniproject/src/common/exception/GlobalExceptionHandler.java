package com.core.miniproject.src.common.exception;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<T>> baseExceptionHandler(BaseException e) {
        log.warn("[AppException Occurs] message: {} HttpStatusCode: {}",
                e.getStatus().getMessage(), e.getStatus().getStatusCode());

        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(BaseResponse.response(e.getStatus()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<T>> MisMatchExceptionHandler(TypeMismatchException e) {
        log.error("[MethodArgumentTypeMismatchException Occurs] error: {} getRequiredType: {} getPropertyName: {}",
                e.getMessage(), e.getRequiredType(), e.getPropertyName());

        return ResponseEntity.badRequest()
                .body(BaseResponse.response(BaseResponseStatus.TYPE_MISMATCH));
    }

    @ExceptionHandler(Exception.class) // 아직 규정하지 못한 예외에 한해 작동
    public ResponseEntity<BaseResponse<T>> exceptionHandler(Exception e) {
        log.error("[InternalServerError Occurs] error: {}", e.getMessage());
        e.printStackTrace();

        return ResponseEntity.internalServerError()
                .body(BaseResponse.response(BaseResponseStatus.INVALID_ERROR));
    }
}
