package com.core.miniproject.src.accommodation.service;

import com.core.miniproject.src.accommodation.domain.dto.DiscountInsertRequest;
import com.core.miniproject.src.accommodation.domain.dto.DiscountInsertResponse;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.DiscountRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationDiscountService {

    private final DiscountRepository discountRepository;

    @Transactional
    public DiscountInsertResponse createDiscount(DiscountInsertRequest request) {

        discountRepository.findDiscountByRate(request.getDiscountRate())
                .ifPresent(rate -> {
                    throw new BaseException(BaseResponseStatus.DUPLICATE_DISCOUNTRATE);
                });

        Discount discount = Discount.builder()
                .discountRate(request.getDiscountRate())
                .build();

        return DiscountInsertResponse.builder()
                .discountRate(discountRepository.save(discount).getDiscountRate())
                .build();
    }
}
