package com.core.miniproject.src.accommodation.service;

import com.core.miniproject.src.accommodation.domain.dto.*;
import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.accommodation.repository.DiscountRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.common.util.AccommodationUploader;
import com.core.miniproject.src.image.domain.entity.AccommodationImage;
import com.core.miniproject.src.image.repository.AccommodationImageRepository;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.location.repository.LocationRepository;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

    private final AccommodationUploader imageUploader;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;
    private final DiscountRepository discountRepository;
    private final LocationRepository locationRepository;
    private final AccommodationImageRepository imageRepository;

    @Transactional
    public AccommodationInsertResponse createAccommodation(
            AccommodationInsertRequest request,
            List<MultipartFile> multipartFile, MemberInfo memberInfo)
    {
        Accommodation accommodation =
                getAccommodationPerDisCountAndLocation(request, multipartFile, memberInfo);

        accommodation.getImages().forEach(image -> image.assignAccommodation(accommodation));

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);


        return AccommodationInsertResponse.toClient(savedAccommodation);
    }

    private Accommodation getAccommodationPerDisCountAndLocation(
            AccommodationInsertRequest request,
            List<MultipartFile> multipartFile,
            MemberInfo memberInfo) {

        Discount discount = discountRepository.findDiscountByRate(request.getDiscountRate())
                .orElseGet(() -> discountRepository.save(Discount.builder().discountRate(request.getDiscountRate()).build()));

        LocationType type = LocationType.getByText(request.getLocationName());

        Location location = locationRepository.findLocationByType(type)
                .orElseGet(() -> locationRepository.save(Location.builder().locationName(type).build()));

        System.out.println("확인할 location = " + location.getLocationName());

        List<AccommodationImage> images = new ArrayList<>();

        if(multipartFile != null) {
            try {
                List<String> imagePaths = imageUploader.upload(multipartFile, "accommodationImages");
                imagePaths.forEach(path -> {
                    AccommodationImage image = AccommodationImage.builder()
                            .imagePath(path)
                            .build();
                    images.add(image);
                });
            } catch (IOException e) {
                throw new IllegalArgumentException("업로드 오류", e);
            }
        }

        List<AccommodationImage> accommodationImages = imageRepository.saveAll(images);

        System.out.println("저장될 location = " + location.getLocationName());

        return Accommodation.builder()
                .memberId(memberInfo.getId())
                .accommodationName(request.getAccommodationName())
                .accommodationType(AccommodationType.getByText(request.getAccommodationType()))
                .introduction(request.getIntroduction())
                .discount(discount)
                .location(location)
                .address(request.getAddress())
                .images(accommodationImages)
                .build();
    }

    @Transactional
    public List<AccommodationResponse> getAllAccommodation() {

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(1);

        List<Accommodation> allAccommodation = accommodationRepository.getAllAccommodation(checkIn, checkOut);

        checkRedundantImages(allAccommodation);

        return allAccommodation.stream()
                .map(AccommodationResponse::toClient)
                .collect(Collectors.toList());
    }

    @Transactional // 수정 전체 조회
    public AccommodationAllResponse findAccommodation(
            LocalDate checkIn, LocalDate checkInOut, String locationType, String accommodationType, Integer personal, Pageable pageable) {

        AccommodationType aType = AccommodationType.getByText(accommodationType);
        LocationType lType = LocationType.getByText(locationType);

        List<Accommodation> allAccommodation = accommodationRepository.findAllAccommodation(checkIn, checkInOut, lType, aType, personal, pageable);
        Integer countAccommodation = accommodationRepository.getCountAccommodation(checkIn, checkInOut, lType, aType, personal);

        checkRedundantImages(allAccommodation);

        List<AccommodationAllDto> accommodationAllDtos = allAccommodation.stream()
                .map(AccommodationAllDto::toClient)
                .collect(Collectors.toList());

        return AccommodationAllResponse.toClient(accommodationAllDtos, countAccommodation);
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

        LocationType type = LocationType.getByText(request.getLocationType());

        Location location = locationRepository.findLocationByType(type).orElseThrow(
                ()-> new BaseException(BaseResponseStatus.LOCATION_NOT_FOUND)
        );
        Discount discount = discountRepository.findDiscountByRate(request.getDiscount()).orElseThrow(
                ()-> new BaseException(BaseResponseStatus.DISCOUNT_NOT_FOUND)
        );

        List<AccommodationImage> images = updateImage(id, request, accommodation);
        List<AccommodationImage> newImages = imageRepository.saveAll(images);
        accommodation.update(request,location,discount, newImages);
        Accommodation accommodation1 = accommodationRepository.save(accommodation);
        return AccommodationResponse.toClient(accommodation1);
    }

    public AccommodationResponse getAccommodationDetail(Long accommodationId, LocalDate checkIn, LocalDate checkOut) {

        Accommodation accommodation = accommodationRepository.accommodationDetailInfo(accommodationId, checkIn, checkOut)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ACCOMMODATION_DOES_NOT_EXIST));

        checkRedundantImages(accommodation);

        return AccommodationResponse.toClient(accommodation);
    }

    public RegisteredAccommodationResponse getAccommodationMember(MemberInfo memberInfo, Pageable pageable) {

        Page<Accommodation> accommodationList = accommodationRepository.accommodationRegisteredMember(memberInfo.getId(), pageable);

        long totalElements = accommodationList.getTotalElements();

        List<RegisteredAccommodationDto> dtoList = accommodationList.stream()
                .map(RegisteredAccommodationDto::toResponse)
                .collect(Collectors.toList());

        return RegisteredAccommodationResponse.toClient(dtoList, totalElements);
    }

    private List<AccommodationImage> updateImage(Long id, AccommodationRequest request, Accommodation accommodation){
        List<AccommodationImage> images = imageRepository.findAllById(id);
        List<String> requestImages = request.getAccommodationImage();
        if (images == null) {
            images = new ArrayList<>();
        }else{
            for(int i = 0; i < request.getAccommodationImage().size(); i++){
                String imagePath = requestImages.get(i);
                AccommodationImage image = getByImagePath(images, imagePath);
                if(image!=null){
                    image.assignImagePath(imagePath);
                }else{
                    AccommodationImage newImage = AccommodationImage.builder()
                            .accommodation(accommodation)
                            .imagePath(imagePath)
                            .build();
                    images.add(newImage);
                }

            }
        }
        return images;
    }

    private AccommodationImage getByImagePath(List<AccommodationImage> images, String path){
        for (AccommodationImage image : images) {
            if(image.getImagePath().equalsIgnoreCase(path)){
                return image;
            }
        }
        return null;
    }

    private void checkRedundantImages(List<Accommodation> accommodations){
        for (Accommodation accommodation : accommodations) {
            Set<Long> imageIds = new HashSet<>();
            List<AccommodationImage> newImages = new ArrayList<>();
            for (AccommodationImage image : accommodation.getImages()) {
                Long imageId = image.getId();
                if (!imageIds.contains(imageId)) {
                    imageIds.add(imageId);
                    newImages.add(image);
                }
            }
            accommodation.assignImages(newImages);
        }
    }

    private void checkRedundantImages(Accommodation accommodation){
        Set<Long> imageIds = new HashSet<>();
        List<AccommodationImage> newImages = new ArrayList<>();
        for (AccommodationImage image : accommodation.getImages()) {
            Long imageId = image.getId();
            if (!imageIds.contains(imageId)) {
                imageIds.add(imageId);
                newImages.add(image);
            }
        }
        accommodation.assignImages(newImages);
    }

}

