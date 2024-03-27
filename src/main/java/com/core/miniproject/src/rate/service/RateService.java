package com.core.miniproject.src.rate.service;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.rate.domain.dto.RateInsertRequest;
import com.core.miniproject.src.rate.domain.dto.RateInsertResponse;
import com.core.miniproject.src.rate.domain.entity.Rate;
import com.core.miniproject.src.rate.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateService {

    private final AccommodationRepository accommodationRepository;
    private final RateRepository rateRepository;

    @Transactional
    public RateInsertResponse createRate(
            Long id,
            RateInsertRequest request,
            MemberInfo memberInfo){
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.ACCOMMODATION_DOES_NOT_EXIST));
        Rate rate = getRateForRequest(request, accommodation);
        return RateInsertResponse.toClient(rateRepository.save(rate));
    }

    private Rate getRateForRequest(RateInsertRequest request, Accommodation accommodation){
        rateValidate(request);
        return Rate.builder()
                .rate(request.getRate())
                .accommodation(accommodation)
                .build();
    }

    private void rateValidate(RateInsertRequest request){
        if (request.getRate() > 5.0){
            throw new BaseException(BaseResponseStatus.RATE_OUT_OF_BOUND_OVER);
        } else if (request.getRate() <0.0) {
            throw new BaseException(BaseResponseStatus.RATE_OUT_OF_BOUND_UNDER);
        }
    }
}
