package com.core.miniproject.src.accommodation.service;

import com.core.miniproject.src.accommodation.domain.dto.AccommodationInsertRequest;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationInsertResponse;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationRequest;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationResponse;
import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.accommodation.repository.DiscountRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.image.domain.entity.AccommodationImage;
import com.core.miniproject.src.image.repository.AccommodationImageRepository;
import com.core.miniproject.src.image.service.AccommodationImageService;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final DiscountRepository discountRepository;
    private final LocationRepository locationRepository;
    private final AccommodationImageRepository imageRepository;
    private final AccommodationImageService imageService;

    @Transactional
    public AccommodationInsertResponse createAccommodation(
            AccommodationInsertRequest request,
            List<String> imageRequest, MemberInfo memberInfo)
    {
        Accommodation accommodation =
                getAccommodationPerDisCountAndLocation(request, imageRequest);

        accommodation.getImages().forEach(image -> image.assignAccommodation(accommodation));

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);


        return AccommodationInsertResponse.toClient(savedAccommodation);
    }

    private Accommodation getAccommodationPerDisCountAndLocation(
            AccommodationInsertRequest request,
            List<String> imageRequest
    ) {

        Discount discount = discountRepository.findDiscountByRate(request.getDiscountRate())
                .orElseGet(() -> discountRepository.save(Discount.builder().discountRate(request.getDiscountRate()).build()));

        Location location = locationRepository.findLocationByType(request.getLocationName())
                .orElseGet(() -> locationRepository.save(Location.builder().locationName(request.getLocationName()).build()));

        List<AccommodationImage> images = imageRequest.stream()
                .map(path -> AccommodationImage.builder()
                        .imagePath(path)
                        .build())
                .collect(Collectors.toList());

        List<AccommodationImage> accommodationImages = imageRepository.saveAll(images);

        return Accommodation.builder()
                .accommodationName(request.getAccommodationName())
                .accommodationType(AccommodationType.getByText(request.getAccommodationType()))
                .introduction(request.getIntroduction())
                .discount(discount)
                .location(location)
                .address(request.getAddress())
                .images(accommodationImages)
                .build();
    }

    //Location->Accommodation->Room->RoomPrice
    //전체 숙소 조회
    /*
     * 0. AccommodationResponse로 전달하기 위해 리스트 생성
     * 1. findAll()로 전체 리스트를 조회
     * 2. toClient로 보내줄 데이터로 가공하여 add 후 return
     * */
    @Transactional // 수정 전체 조회
    public List<AccommodationResponse> findAllAccommodation(Pageable pageable) {

        List<Accommodation> allAccommodation = accommodationRepository.getAllAccommodation(pageable);
//        int countAccommodation = accommodationRepository.getCountAccommodation();
//
//        System.out.println("countAccommodation = " + countAccommodation);

        return allAccommodation.stream()
                .map(AccommodationResponse::toClient)
                .collect(Collectors.toList());
    }

    //타입별 숙소 조회(수정)
    @Transactional
    public List<AccommodationResponse> findAccommodationByType(String text, Pageable pageable) {
        AccommodationType type = AccommodationType.getByText(text);
        List<Accommodation> accommodations = accommodationRepository.findByAccommodationType(type, pageable);
        return accommodations.stream()
                .map(AccommodationResponse::toClient)
                .collect(Collectors.toList());
    }

    //위치별 숙소 조회(수정)
    @Transactional
    public List<AccommodationResponse> findAccommodationByLocation(LocationType type, Pageable pageable) {

        List<Accommodation> accommodations = accommodationRepository.findByLocationType(type, pageable);

        return accommodations.stream()
                .map(AccommodationResponse::toClient)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AccommodationResponse> findByAccommodationAndLocation(AccommodationType aType, LocationType lType, Pageable pageable) {

        List<Accommodation> accommodations = accommodationRepository.findByAccommodationTypeAndLocationType(aType, lType, pageable);
        return accommodations.stream()
                .map(AccommodationResponse::toClient)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AccommodationResponse> findByLocationAndPersonal(LocationType type, int fixedMember, Pageable pageable){

        List<Accommodation> accommodations = accommodationRepository.findByLocationTypeAndFixedNumber(type, fixedMember, pageable);
        return accommodations.stream()
                .map(AccommodationResponse::toClient)
                .collect(Collectors.toList());
    }


    @Transactional
    public BaseResponseStatus deleteAccommodation(Long id, MemberInfo memberInfo){
        Accommodation accommodation = accommodationRepository.findByAccommodationId(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.ACCOMMODATION_DOES_NOT_EXIST));
        try {
            accommodationRepository.deleteById(accommodation.getId());
            return BaseResponseStatus.DELETE_SUCCESS;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DELETE_FAIL);
        }
    }

    @Transactional
    public AccommodationResponse updateAccommodation(Long id, AccommodationRequest request, MemberInfo memberInfo){
        Accommodation accommodation = accommodationRepository.findByAccommodationId(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.ACCOMMODATION_DOES_NOT_EXIST));
        Location location = locationRepository.findLocationByType(request.getLocationType()).orElseThrow(
                ()-> new BaseException(BaseResponseStatus.LOCATION_NOT_FOUND)
        );
        Discount discount = discountRepository.findDiscountByRate(request.getDiscount()).orElseThrow(
                ()-> new BaseException(BaseResponseStatus.DISCOUNT_NOT_FOUND)
        );
        accommodation.update(request,location,discount);
        Accommodation accommodation1 = accommodationRepository.save(accommodation);
        return AccommodationResponse.toClient(accommodation1);
    }

    public AccommodationResponse getAccommodationDetail(Long accommodationId) {

        Accommodation accommodation = accommodationRepository.findByAccommodationId(accommodationId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ACCOMMODATION_DOES_NOT_EXIST));

        return AccommodationResponse.toClient(accommodation);
    }
}

