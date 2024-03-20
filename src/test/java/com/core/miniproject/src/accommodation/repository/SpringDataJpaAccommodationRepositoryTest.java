package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.AccommodationDto;
import com.core.miniproject.src.accommodation.domain.AccommodationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
class SpringDataJpaAccommodationRepositoryTest {

    @Autowired
    SpringDataJpaAccommodationRepository springDataJpaAccommodationRepository;

    @Test
    void create(){
        //given
        AccommodationDto accommodationDto = new AccommodationDto();
        accommodationDto.setIntroduction("소개합니다 호텔");
        accommodationDto.setAccommodationName("테스트 호텔");
        accommodationDto.setAccommodationType(AccommodationType.HOTEL);
        accommodationDto.setAccommodationImage("이미지 링크");

        //when
        AccommodationDto newAccomm = springDataJpaAccommodationRepository.save(accommodationDto);

        //then
        Assertions.assertThat(accommodationDto.getAccommodationName()).isEqualTo(newAccomm.getAccommodationName());
    }

    @Test
    void 전체_목록_조회_성공(){
        //given
        AccommodationDto accommodationDto = new AccommodationDto();
        accommodationDto.setIntroduction("소개합니다 호텔");
        accommodationDto.setAccommodationName("테스트 호텔");
        accommodationDto.setAccommodationType(AccommodationType.HOTEL);
        accommodationDto.setAccommodationImage("이미지 링크");

        //when
        List<AccommodationDto> accommodationDtoList = springDataJpaAccommodationRepository.findAll();
        //then
        Assertions.assertThat(accommodationDtoList).isNotNull();
    }

    //데이터가 없는데 조회를 하는 경우
    @Test
    void 전체_목록_조회_실패(){
        //given

        //when
        List<AccommodationDto> accommodationDtoList = springDataJpaAccommodationRepository.findAll();
        //then
        Assertions.assertThat(accommodationDtoList).isEmpty();
    }

    //호텔, 모텔 등 종류별 조회
    @Test
    void 숙소종류별로조회_성공(){
        //given
        AccommodationDto accommodationDto1 = new AccommodationDto();
        accommodationDto1.setIntroduction("소개합니다 호텔");
        accommodationDto1.setAccommodationName("테스트 호텔");
        accommodationDto1.setAccommodationType(AccommodationType.HOTEL);
        accommodationDto1.setAccommodationImage("이미지 링크");

        AccommodationDto accommodationDto2 = new AccommodationDto();
        accommodationDto2.setIntroduction("소개합니다 모텔");
        accommodationDto2.setAccommodationName("테스트 모텔");
        accommodationDto2.setAccommodationType(AccommodationType.MOTEL);
        accommodationDto2.setAccommodationImage("이미지 링크");
        //when
        springDataJpaAccommodationRepository.save(accommodationDto1);
        Optional<AccommodationDto> hotel = springDataJpaAccommodationRepository.findByAccommodationType(AccommodationType.HOTEL);

        //then
        Assertions.assertThat(hotel.get().getAccommodationType()).isEqualTo(accommodationDto1.getAccommodationType());
    }

    //호텔, 모텔 등 종류별 조회 실패
    @Test
    void 숙소종류별로조회_실패(){
        //given
        AccommodationDto accommodationDto1 = new AccommodationDto();
        accommodationDto1.setIntroduction("소개합니다 호텔");
        accommodationDto1.setAccommodationName("테스트 호텔");
        accommodationDto1.setAccommodationType(AccommodationType.HOTEL);
        accommodationDto1.setAccommodationImage("이미지 링크");

        AccommodationDto accommodationDto2 = new AccommodationDto();
        accommodationDto2.setIntroduction("소개합니다 모텔");
        accommodationDto2.setAccommodationName("테스트 모텔");
        accommodationDto2.setAccommodationType(AccommodationType.MOTEL);
        accommodationDto2.setAccommodationImage("이미지 링크");
        //when
        springDataJpaAccommodationRepository.save(accommodationDto1);
        Optional<AccommodationDto> hotel = springDataJpaAccommodationRepository.findByAccommodationType(AccommodationType.HOTEL);

        //then
        Assertions.assertThat(hotel.get().getAccommodationType()).isNotEqualTo(accommodationDto2.getAccommodationType());
    }
}