package com.core.miniproject.src.accommodation.service;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationDiscountService {

    private final DiscountRepository discountRepository;

    public Discount createDiscount(Accommodation accommodation, Double rate) {

        Discount discount = Discount.builder()
                .discountRate(10.0)
                .build();
        return discountRepository.save(discount);
    }
}
