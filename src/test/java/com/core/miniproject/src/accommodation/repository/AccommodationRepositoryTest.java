package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.location.repository.LocationRepository;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import com.core.miniproject.src.roomprice.domain.RoomPrice;
import com.core.miniproject.src.roomprice.repository.RoomPriceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//테스트 코드 커밋
@DataJpaTest
class AccommodationRepositoryTest {

    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomPriceRepository roomPriceRepository;
    @Autowired
    LocationRepository locationRepository;

    @Test //room 테이블 연관관계 추가
    void create(){
        Room room1 = Room.builder()
                .roomName("테스트 호텔 디럭스 룸")
                .roomInfo("깨끗한 객실")
                .roomCount(200)
                .fixedMember(2)
                .maxedMember(4)
                .roomPrice(RoomPrice.builder()
                        .price(200000)
                        .build())
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .roomPrice(RoomPrice.builder()
                        .price(250000)
                        .build())
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
        Assertions.assertThat(newAccomm.getRoomId().get(0).getRoomPrice().getPrice()).isEqualTo(200000);
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
                .roomPrice(RoomPrice.builder()
                        .price(200000)
                        .build())
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .roomPrice(RoomPrice.builder()
                        .price(250000)
                        .build())
                .build();

        Accommodation accommodation = Accommodation.builder()
                .location(Location.builder().id(1L).locationName(LocationType.SEOUL).build())
                .roomId(Arrays.asList(room1, room2))
                .introduction("소개")
                .accommodationName("accommodationName")
                .accommodationType(AccommodationType.MOTEL)
                .accommodationImage("image")
                .rate(5.0)
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
                .roomPrice(RoomPrice.builder()
                        .price(200000)
                        .build())
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .roomPrice(RoomPrice.builder()
                        .price(250000)
                        .build())
                .build();

        Accommodation accommodation = Accommodation.builder()
                .location(Location.builder().id(1L).locationName(LocationType.SEOUL).build())
                .roomId(Arrays.asList(room1, room2))
                .introduction("소개")
                .accommodationName("accommodationName")
                .accommodationType(AccommodationType.MOTEL)
                .accommodationImage("image")
                .rate(5.0)
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
                .roomPrice(RoomPrice.builder()
                        .price(200000)
                        .build())
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .roomPrice(RoomPrice.builder()
                        .price(250000)
                        .build())
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
                .rate(5.0)
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
                .roomPrice(RoomPrice.builder()
                        .price(200000)
                        .build())
                .build();

        Room room2 = Room.builder()
                .roomName("테스트 호텔 슈페리어 룸")
                .roomInfo("편안한 객실")
                .roomCount(150)
                .fixedMember(2)
                .maxedMember(3)
                .roomPrice(RoomPrice.builder()
                        .price(250000)
                        .build())
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
                .rate(5.0)
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
}