package com.core.miniproject.src.common.security.jwt;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {

        private String secretKey;
        private String grantType;
        private Long expiredIn;
    }
