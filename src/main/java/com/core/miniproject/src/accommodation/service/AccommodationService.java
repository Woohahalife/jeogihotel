package com.core.miniproject.src.accommodation.service;

import com.core.miniproject.src.accommodation.domain.dto.AccommodationInsertRequest;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationInsertResponse;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationResponse;
import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.accommodation.repository.DiscountRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.location.repository.LocationRepository;
import com.core.miniproject.src.rate.repository.RateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final DiscountRepository discountRepository;
    private final LocationRepository locationRepository;
    @PersistenceContext EntityManager entityManager;

    @Transactional
    public AccommodationInsertResponse createAccommodation(
            AccommodationInsertRequest request,
            MemberInfo memberInfo) {
        Accommodation accommodation =
                getAccommodationPerDisCountAndLocation(request);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        accommodationRepository.updateRate(savedAccommodation.getId());
        entityManager.refresh(savedAccommodation);

        return AccommodationInsertResponse.toClient(savedAccommodation);
    }

    private Accommodation getAccommodationPerDisCountAndLocation(AccommodationInsertRequest request) {
        Discount discount = discountRepository.findDiscountByRate(request.getDiscountRate())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DISCOUNT_NOT_FOUND));
        Location location = locationRepository.findLocationByType(request.getLocationName())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LOCATION_NOT_FOUND));

        return Accommodation.builder()
                .accommodationName(request.getAccommodationName())
                .accommodationImage(request.getAccommodationImage())
                .accommodationType(request.getAccommodationType())
                .introduction(request.getIntroduction())
                .rate(0.0) // 아직 정해지지 않음
                .price(request.getPrice())
                .discount(discount)
                .location(location)
                .build();
    }

    //Location->Accommodation->Room->RoomPrice
    //전체 숙소 조회
    /*
     * 0. AccommodationResponse로 전달하기 위해 리스트 생성
     * 1. findAll()로 전체 리스트를 조회
     * 2. toClient로 보내줄 데이터로 가공하여 add 후 return
     * */
    @Transactional
    public List<AccommodationResponse> findAllAccommodation() {
        List<AccommodationResponse> accommodationResponses = new ArrayList<>();
        List<Accommodation> accommodations = accommodationRepository.findAll();
        for (Accommodation accommodation : accommodations) {
            accommodationResponses.add(AccommodationResponse.toClient(accommodation));
            log.info("Accommodation: accommodationName= {}, accommodationType= {} accommodationImage= {} introduction= {} price= {} rate= {}"
                    , accommodation.getAccommodationName(), accommodation.getAccommodationType(),
                    accommodation.getAccommodationImage(), accommodation.getIntroduction(),
                    accommodation.getPrice(), accommodation.getRate());
        }
        return accommodationResponses;
    }

    //타입별 숙소 조회
    @Transactional
    public List<AccommodationResponse> findAccommodationByType(AccommodationType type) {
        List<AccommodationResponse> responses = new ArrayList<>();
        List<Accommodation> accommodations = accommodationRepository.findByAccommodationType(type);
        return getAccommodationResponses(responses, accommodations);
    }

    //위치별 숙소 조회
    @Transactional
    public List<AccommodationResponse> findAccommodationByLocation(LocationType type) {
        List<AccommodationResponse> responses = new ArrayList<>();
        List<Accommodation> accommodations = accommodationRepository.findByLocationType(type);
        return getAccommodationResponses(responses, accommodations);
    }

    @Transactional
    public List<AccommodationResponse> findByAccommodationAndLocation(AccommodationType aType, LocationType lType) {
        List<AccommodationResponse> responses = new ArrayList<>();
        List<Accommodation> accommodations = accommodationRepository.findByAccommodationTypeAndLocationType(aType, lType);
        return getAccommodationResponses(responses, accommodations);
    }

    @Transactional
    public List<AccommodationResponse> findByLocationAndPersonal(LocationType type, int fixedMember){
        List<AccommodationResponse> responses = new ArrayList<>();
        List<Accommodation> accommodations = accommodationRepository.findByLocationTypeAndFixedNumber(type, fixedMember);
        return getAccommodationResponses(responses, accommodations);
    }

    private List<AccommodationResponse> getAccommodationResponses(List<AccommodationResponse> responses, List<Accommodation> accommodations) {
        for (Accommodation accommodation : accommodations) {
            AccommodationResponse response = AccommodationResponse.toClient(accommodation);
            log.info("Accommodation: accommodationName= {}, accommodationType= {} accommodationImage= {} introduction= {} price= {} rate= {}"
                    , accommodation.getAccommodationName(), accommodation.getAccommodationType(),
                    accommodation.getAccommodationImage(), accommodation.getIntroduction(),
                    accommodation.getPrice(), accommodation.getRate());
            responses.add(response);
        }
        return responses;
    }
}

