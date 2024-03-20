package com.core.miniproject.src.common.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BaseResponseTest {

    @Test
    void 공통응답_테스트_성공() {

        // given
        String testData = "Test data";

        // when
        BaseResponse<String> response = BaseResponse.response(testData);

        // then
        assertThat(response.getIsSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(BaseResponseStatus.SUCCESS.getStatusCode());
        assertThat(response.getMessage()).isEqualTo(BaseResponseStatus.SUCCESS.getMessage());
        assertThat(response.getData()).isEqualTo(testData);
    }

    @Test
    void 공통응답_테스트_실패() {

        // given
        BaseResponseStatus errorStatus = BaseResponseStatus.INVALID_ERROR;

        // when
        BaseResponse<BaseResponseStatus> response = BaseResponse.response(errorStatus);

        // Then
        assertThat(response.getIsSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(errorStatus.getStatusCode());
        assertThat(response.getMessage()).isEqualTo(errorStatus.getMessage());
        assertThat(response.getStatus()).isEqualTo(errorStatus);
        assertThat(response.getData()).isNull();
    }
}
