package com.core.miniproject.src.rate.service;

import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.rate.domain.dto.RateInsertRequest;
import com.core.miniproject.src.rate.domain.dto.RateInsertResponse;
import com.core.miniproject.src.rate.domain.entity.Rate;
import com.core.miniproject.src.rate.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateService {

    private final RateRepository rateRepository;

//    public RateInsertResponse registerRate(
//            RateInsertRequest request,
//            MemberInfo memberInfo){
//
//        return null;
//    }
}
