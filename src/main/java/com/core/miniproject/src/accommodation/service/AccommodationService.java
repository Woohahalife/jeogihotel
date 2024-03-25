package com.core.miniproject.src.accommodation.service;

import com.core.miniproject.src.accommodation.domain.dto.AccommodationInsertRequest;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationInsertResponse;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationResponse;
import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.repository.SpringDataJpaAccommodationRepository;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

    private final SpringDataJpaAccommodationRepository springDataJpaAccommodationRepository;

    //Location->Accommodation->Room->RoomPrice
    //전체 숙소 조회
    /*
    * 0. AccommodationResponse로 전달하기 위해 리스트 생성
    * 1. findAll()로 전체 리스트를 조회
    * 2. toClient로 보내줄 데이터로 가공하여 add 후 return
    * */
    public List<AccommodationResponse> findAllAccommodation(){
        List<AccommodationResponse> accommodationResponses = new ArrayList<>();
        List<Accommodation> accommodations = springDataJpaAccommodationRepository.findAll();
        for (Accommodation accommodation : accommodations) {
            accommodationResponses.add(AccommodationResponse.toClient(accommodation));
            log.info("Accommodation: accommodationName= {}, accommodationType= {} accommodationImage= {} introduction= {} price= {} rate= {}"
            ,accommodation.getAccommodationName(),accommodation.getAccommodationType(),
            accommodation.getAccommodationImage(), accommodation.getIntroduction(),
                    accommodation.getPrice(), accommodation.getRate());
        }
        return accommodationResponses;
    }

//    public AccommodationInsertResponse createAccommodation(
//            AccommodationInsertRequest request,
//            MemberInfo memberInfo)
//    {
//        acc
//
//
//
//    }
}
