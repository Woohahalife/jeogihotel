package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.location.repository.LocationRepository;
import com.core.miniproject.src.rate.domain.entity.Rate;
import com.core.miniproject.src.rate.repository.RateRepository;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//테스트 코드 커밋
@DataJpaTest
class AccommodationRepositoryTest {

    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    RateRepository rateRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test //room 테이블 연관관계 추가
    void create(){
        Room room1 = Room.builder()
                .roomName("테스트 호텔 디럭스 룸")
                .roomInfo("깨끗한 객실")
                .roomCount(200)
                .fixedMember(2)
                .maxedMember(4)
                .price(200000)
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .price(200000)
                .build();
        // Accommodation 생성
        Accommodation accommodation = Accommodation.builder()
                .introduction("테스트 호텔입니다.")
                .accommodationImage("이미지 링크입니다.")
                .accommodationType(AccommodationType.HOTEL)
                .accommodationName("테스트 호텔")
                .roomId(Arrays.asList(room1, room2)) // Room 리스트 설정
                .location(Location.builder().id(1L).build())
                .discount(Discount.builder().id(1L).build())
                .build();
        //when
        Accommodation newAccomm = accommodationRepository.save(accommodation);

        //then
        System.out.println("newAccomm.getLocationId() = " + newAccomm.getLocation());
        Assertions.assertThat(newAccomm.getRoomId().get(0).getPrice()).isEqualTo(200000);
    }

    @Test
    void 전체_목록_조회_성공(){
        // given
        Room room1 = Room.builder()
                .roomName("테스트 호텔 디럭스 룸")
                .roomInfo("깨끗한 객실")
                .roomCount(200)
                .fixedMember(2)
                .maxedMember(4)
                .price(200000)
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .price(200000)
                .build();

        Accommodation accommodation = Accommodation.builder()
                .location(Location.builder().id(1L).locationName(LocationType.SEOUL).build())
                .roomId(Arrays.asList(room1, room2))
                .introduction("소개")
                .accommodationName("accommodationName")
                .accommodationType(AccommodationType.MOTEL)
                .accommodationImage("image")
                .rates(Collections.singletonList(Rate.builder().id(1L).build()))
                .discount(Discount.builder().id(1L).discountRate(10.0).build())
                .build();

        Accommodation save = accommodationRepository.save(accommodation);

        // when
        List<Accommodation> accommodationList = accommodationRepository.findAll();

        // then
        Assertions.assertThat(accommodationList.size()).isEqualTo(1);
        Assertions.assertThat(accommodationList.get(0).getRoomId().size())
                .isEqualTo(save.getRoomId().size());
        Assertions.assertThat(accommodationList.get(0).getDiscount().getId())
                .isEqualTo(save.getDiscount().getId());
        Assertions.assertThat(accommodationList.get(0).getLocation().getId())
                .isEqualTo(save.getLocation().getId());
        Assertions.assertThat(accommodationList.get(0).getAccommodationType())
                .isEqualTo(AccommodationType.MOTEL);

    }

    //데이터가 없는데 조회를 하는 경우
    @Test
    void 전체_목록_조회_실패(){
        //given

        //when
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        //then
        Assertions.assertThat(accommodationList).isEmpty();
    }

    //호텔, 모텔 등 종류별 조회
    @Test
    void 숙소종류별로조회_성공(){
        //given
        Room room1 = Room.builder()
                .roomName("테스트 호텔 디럭스 룸")
                .roomInfo("깨끗한 객실")
                .roomCount(200)
                .fixedMember(2)
                .maxedMember(4)
                .price(20000)
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .price(20000)
                .build();

        Accommodation accommodation = Accommodation.builder()
                .location(Location.builder().id(1L).locationName(LocationType.SEOUL).build())
                .roomId(Arrays.asList(room1, room2))
                .introduction("소개")
                .accommodationName("accommodationName")
                .accommodationType(AccommodationType.MOTEL)
                .accommodationImage("image")
                .rates(Collections.singletonList(Rate.builder().id(1L).build()))
                .discount(Discount.builder().id(1L).discountRate(10.0).build())
                .build();

        Accommodation save = accommodationRepository.save(accommodation);
        //when
        List<Accommodation> hotel = accommodationRepository
                .findByAccommodationType(AccommodationType.MOTEL);

        //then
        Assertions.assertThat(hotel.size()).isEqualTo(1);
    }

    @Test
    void 숙소위치별로조회_성공(){
        //given
        Room room1 = Room.builder()
                .roomName("테스트 호텔 디럭스 룸")
                .roomInfo("깨끗한 객실")
                .roomCount(200)
                .fixedMember(2)
                .maxedMember(4)
                .price(200000)
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .price(200000)
                .build();

        Location location = Location.builder()
                .locationName(LocationType.SEOUL)
                .build();

        Accommodation accommodation = Accommodation.builder()
                .location(location)
                .roomId(Arrays.asList(room1, room2))
                .introduction("소개")
                .accommodationName("accommodationName")
                .accommodationType(AccommodationType.MOTEL)
                .accommodationImage("image")
                .rates(Collections.singletonList(Rate.builder().id(1L).build()))
                .discount(Discount.builder().id(1L).discountRate(10.0).build())
                .build();
        //when
        locationRepository.save(location);
        Accommodation save = accommodationRepository.save(accommodation);
        List<Accommodation> seoulHotel = accommodationRepository.findByLocationType(LocationType.SEOUL);

        //then
        Assertions.assertThat(seoulHotel.get(0).getLocation().getLocationName())
                .isEqualTo(accommodation.getLocation().getLocationName());
    }

    @Test
    void 숙소위치와타입별로조회_성공(){

        //given
        Room room1 = Room.builder()
                .roomName("테스트 호텔 디럭스 룸")
                .roomInfo("깨끗한 객실")
                .roomCount(200)
                .fixedMember(2)
                .maxedMember(4)
                .price(200000)
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .price(200000)
                .build();

        Location location = Location.builder()
                .locationName(LocationType.SEOUL)
                .build();

        Accommodation accommodation = Accommodation.builder()
                .location(location)
                .roomId(Arrays.asList(room1, room2))
                .introduction("소개")
                .accommodationName("accommodationName")
                .accommodationType(AccommodationType.MOTEL)
                .accommodationImage("image")
                .rates(Collections.singletonList(Rate.builder().id(1L).build()))
                .discount(Discount.builder().id(1L).discountRate(10.0).build())
                .build();

        //when
        locationRepository.save(location);
        accommodationRepository.save(accommodation);
        List<Accommodation> seoulHotel = accommodationRepository.findByAccommodationTypeAndLocationType(
                accommodation.getAccommodationType(),
                accommodation.getLocation().getLocationName());

        //then
        Assertions.assertThat(seoulHotel.get(0).getAccommodationType()).isEqualTo(accommodation.getAccommodationType());
    }

    @Test
    void 숙소위치와인원수별로조회_성공() {

        //given
        Location location = Location.builder()
                .locationName(LocationType.SEOUL)
                .build();

        Discount discount = Discount.builder()
                .discountRate(0.3)
                .build();

        Discount newDiscount = discountRepository.save(discount);

        Accommodation accommodation = Accommodation.builder()
                .introduction("테스트 호텔입니다.")
                .accommodationImage("이미지 링크입니다.")
                .accommodationType(AccommodationType.HOTEL)
                .accommodationName("테스트 호텔")
                .roomId(null)
                .location(location)
                .discount(newDiscount)
                .build();

        Room room = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(accommodation)
                .price(200000)
                .build();


        //when
        locationRepository.save(location);
        accommodationRepository.save(accommodation);
        roomRepository.save(room);

        List<Accommodation> accommodations = accommodationRepository.findByLocationTypeAndFixedNumber(
                accommodation.getLocation().getLocationName(),
                room.getFixedMember());


        //then
        //숙소의 정보를 넘겨 주기 때문에 객실에 대한 정보는 가지고 오지 않아도 됨.
        Assertions.assertThat(accommodations.get(0).getAccommodationName()).isEqualTo(accommodation.getAccommodationName());
    }

    @Test
    @Transactional
    void 별점_평균_업데이트_성공(){
        //given
        Location location = Location.builder()
                .locationName(LocationType.SEOUL)
                .build();

        Discount discount = Discount.builder()
                .discountRate(0.3)
                .build();

        Discount newDiscount = discountRepository.save(discount);

        Room room = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .price(200000)
                .build();

        Rate rate= Rate.builder()
                .rate(4.5)
                .build();

        Accommodation accommodation = Accommodation.builder()
                .introduction("테스트 호텔입니다.")
                .accommodationImage("이미지 링크입니다.")
                .accommodationType(AccommodationType.HOTEL)
                .accommodationName("테스트 호텔")
                .roomId(null)
                .location(location)
                .discount(newDiscount)
                .rates(Collections.singletonList(rate))
                .build();

        //when
        locationRepository.save(location);
        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        Rate newRate = rateRepository.save(rate);
        roomRepository.save(room);

        Accommodation result = accommodationRepository.findByAccommodationId(1L).orElseThrow();

        //then
        Assertions.assertThat(newRate.getRate()).isEqualTo(4.5);
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getIntroduction()).isEqualTo("테스트 호텔입니다.");
        Assertions.assertThat(result.getAverageRate()).isEqualTo(4.5);

    }

    @Test
    @Transactional
    void 가격과_별점_업데이트_성공(){
        //given
        Location location = Location.builder()
                .locationName(LocationType.SEOUL)
                .build();

        Discount discount = Discount.builder()
                .discountRate(0.3)
                .build();

        Discount newDiscount = discountRepository.save(discount);

        Accommodation accommodation = Accommodation.builder()
                .introduction("테스트 호텔입니다.")
                .accommodationImage("이미지 링크입니다.")
                .accommodationType(AccommodationType.HOTEL)
                .accommodationName("테스트 호텔")
                .roomId(null)
                .location(location)
                .discount(newDiscount)
                .build();

        Room room1 = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(accommodation)
                .price(200000)
                .build();
        Room room2 = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(accommodation)
                .price(100000)
                .build();

        Rate rate= Rate.builder()
                .accommodation(accommodation)
                .rate(4.5)
                .build();
        //when
        locationRepository.save(location);
        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        roomRepository.save(room1);
        roomRepository.save(room2);
        rateRepository.save(rate);
        accommodationRepository.updateRate(newAccommodation.getId());
        accommodationRepository.updatePrice(newAccommodation.getId());
        entityManager.refresh(newAccommodation);

        //then
        Assertions.assertThat(newAccommodation.getPrice()).isEqualTo(room2.getPrice());
    }

}