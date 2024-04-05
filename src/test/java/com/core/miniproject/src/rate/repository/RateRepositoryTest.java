package com.core.miniproject.src.rate.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.accommodation.repository.DiscountRepository;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.location.repository.LocationRepository;
import com.core.miniproject.src.rate.domain.entity.Rate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RateRepositoryTest {

    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    RateRepository rateRepository;

    @Test
    void create(){
        //given
        Location location = Location.builder()
                .locationName(LocationType.BUSAN)
                .build();

        Location newLocation = locationRepository.save(location);

        Discount discount = Discount.builder()
                .discountRate(0.3)
                .build();
        Discount newDiscount = discountRepository.save(discount);

        Accommodation accommodation = Accommodation.builder()
                .accommodationName("호텔")
                .location(newLocation)
                .discount(newDiscount)
                .build();
        Accommodation newAccommodation = accommodationRepository.save(accommodation);

        Rate rate= Rate.builder()
                .rate(4.5)
                .accommodation(newAccommodation)
                .build();

        //when
        Rate newRate = rateRepository.save(rate);

        //then
        Assertions.assertThat(newRate.getRate()).isEqualTo(rate.getRate());
    }

    @Test
    void 객실_별로_조회_성공() {
        //given
        Location location = Location.builder()
                .locationName(LocationType.BUSAN)
                .build();

        Location newLocation = locationRepository.save(location);

        Discount discount = Discount.builder()
                .discountRate(0.3)
                .build();
        Discount newDiscount = discountRepository.save(discount);

        Accommodation accommodation = Accommodation.builder()
                .accommodationName("호텔")
                .location(newLocation)
                .discount(newDiscount)
                .build();
        Accommodation newAccommodation = accommodationRepository.save(accommodation);

        Rate rate= Rate.builder()
                .rate(4.5)
                .accommodation(newAccommodation)
                .build();

        //when
        rateRepository.save(rate);
        List<Rate> rates = rateRepository.findByAccommodationId(newAccommodation.getId());

        //then
        Assertions.assertThat(rates.get(0).getRate()).isEqualTo(rate.getRate());
    }
}