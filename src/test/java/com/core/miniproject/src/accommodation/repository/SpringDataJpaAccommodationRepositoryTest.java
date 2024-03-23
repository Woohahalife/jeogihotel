package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.location.repository.SpringDataLocationRepository;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.SpringDataJpaRoomRepository;
import com.core.miniproject.src.roomprice.domain.RoomPrice;
import com.core.miniproject.src.roomprice.repository.SpringDataJpaRoomPriceRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@Transactional
class SpringDataJpaAccommodationRepositoryTest {

    @Autowired
    SpringDataJpaAccommodationRepository springDataJpaAccommodationRepository;

    @Autowired
    SpringDataJpaRoomRepository springDataJpaRoomRepository;

    @Autowired
    SpringDataJpaRoomPriceRepository springDataJpaRoomPriceRepository;

    @Autowired
    SpringDataLocationRepository springDataLocationRepository;

    @Test //room 테이블 연관관계 추가
    void create(){
        // Location 생성
        Location location = Location.builder()
                .locationName(LocationType.GANGNEUNG)
                .build();

        // Room 생성
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
                .locationId(location) // Location 설정
                .build();
        //when
        Accommodation newAccomm = springDataJpaAccommodationRepository.save(accommodation);

        //then
        System.out.println("newAccomm.getLocationId() = " + newAccomm.getLocationId());
        Assertions.assertThat(newAccomm.getRoomId().get(0).getRoomPrice().getPrice()).isEqualTo(200000);
    }

    @Test
    void 전체_목록_조회_성공(){
        //given
        //Location->Accommodation->Room->RoomPrice
        Location location = Location.builder()
                .accommodationId(Arrays.asList(Accommodation.builder()
                        .roomId(Arrays.asList(Room.builder()
                                .roomCount(20)
                                .roomPrice(RoomPrice.builder()
                                        .price(20000)
                                        .build())
                                .roomInfo("객실은 깨끗")
                                .fixedMember(2)
                                .maxedMember(4)
                                .roomName("디럭스")
                                .build()))
                        .accommodationImage("이미지 링크")
                        .accommodationName("테스트 호텔")
                        .accommodationType(AccommodationType.HOTEL)
                        .introduction("소개 입니다.")
                        .rate(4.5)
                        .price(20000)
                        .build()))
                .locationName(LocationType.BUSAN)
                .build();

        //when
        springDataLocationRepository.save(location);
        springDataJpaRoomPriceRepository.save(location.getAccommodationId().get(0).getRoomId().get(0).getRoomPrice());
        springDataJpaRoomRepository.save(location.getAccommodationId().get(0).getRoomId().get(0));
        springDataJpaAccommodationRepository.save(location.getAccommodationId().get(0));

        List<Accommodation> accommodationList = springDataJpaAccommodationRepository.findAll();
        //then
        Assertions.assertThat(accommodationList.size()).isEqualTo(1);
    }

    //데이터가 없는데 조회를 하는 경우
    @Test
    void 전체_목록_조회_실패(){
        //given

        //when
        List<Accommodation> accommodationList = springDataJpaAccommodationRepository.findAll();
        //then
        Assertions.assertThat(accommodationList).isEmpty();
    }

    //호텔, 모텔 등 종류별 조회
    @Test
    void 숙소종류별로조회_성공(){
        //given
        Accommodation accommodation = Accommodation.builder()
                .introduction("테스트 호텔입니다.")
                .accommodationImage("이미지 링크입니다.")
                .accommodationType(AccommodationType.HOTEL)
                .accommodationName("테스트 호텔")
                .roomId(null)
                .locationId(null)
                .build();

        Room room = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(accommodation)
                .roomPrice(null)
                .build();

        RoomPrice roomPrice = RoomPrice.builder()
                .price(200000)
                .build();

        Location location = Location.builder()
                .locationName(LocationType.SEOUL)
                .build();

        Room.builder()
                .roomPrice(roomPrice)
                .build();

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        Accommodation.builder()
                .locationId(location)
                .roomId(rooms)
                .build();
        //when
        springDataJpaAccommodationRepository.save(accommodation);
        springDataJpaRoomRepository.save(room);
        Optional<Accommodation> hotel = springDataJpaAccommodationRepository.findByAccommodationType(AccommodationType.HOTEL);

        //then
        Assertions.assertThat(hotel.get().getAccommodationType()).isEqualTo(accommodation.getAccommodationType());
    }
}