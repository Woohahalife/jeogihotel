package com.core.miniproject.src.common.exception;

import com.core.miniproject.src.common.response.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final BaseResponseStatus status;
    private String message;

    public BaseException(BaseResponseStatus status) {
        this.status = status;
        this.message = "";
    }

    public BaseException(BaseResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        if(message.isEmpty()) {
            return status.getMessage();
        }
        return String.format("%s ,detail: %s", status.getMessage(), message);
    }
}
