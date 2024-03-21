package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.Accommodation;
import com.core.miniproject.src.accommodation.domain.AccommodationType;
import com.core.miniproject.src.room.domain.Room;
import com.core.miniproject.src.room.repository.SpringDataJpaRoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@DataJpaTest
class SpringDataJpaAccommodationRepositoryTest {

    @Autowired
    SpringDataJpaAccommodationRepository springDataJpaAccommodationRepository;

    @Autowired
    SpringDataJpaRoomRepository springDataJpaRoomRepository;

    @Test //room 테이블 연관관계 추가
    void create(){
        //given
        Accommodation accommodation = new Accommodation();
        accommodation.setIntroduction("소개합니다 호텔");
        accommodation.setAccommodationName("테스트 호텔");
        accommodation.setAccommodationType(AccommodationType.HOTEL);
        accommodation.setAccommodationImage("이미지 링크");

        Room room = new Room();
        room.setRoomInfo("이 방은 테스트 호텔의 방");
        room.setRoomName("방 이름");
        room.setRoomCount(1);
        room.setFixedMember(2);
        room.setMaxedMember(4);
        room.setAccommodation(accommodation);

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        accommodation.setRoomId(rooms);

        //when
        Accommodation newAccomm = springDataJpaAccommodationRepository.save(accommodation);
        Room newRoom = springDataJpaRoomRepository.save(room);

        //then
        //1. 객실에 숙소가 잘 매핑 되었는지
//      Assertions.assertThat(newRoom.getAccommodation().getId()).isEqualTo(newAccomm.getId());
        //2. 숙소에 객실이 잘 매핑 되었는지
        Assertions.assertThat(newRoom).isEqualTo(newAccomm.getRoomId().get(0));
    }

    @Test
    void 전체_목록_조회_성공(){
        //given
        Accommodation accommodation = new Accommodation();
        accommodation.setIntroduction("소개합니다 호텔");
        accommodation.setAccommodationName("테스트 호텔");
        accommodation.setAccommodationType(AccommodationType.HOTEL);
        accommodation.setAccommodationImage("이미지 링크");

        Room room = new Room();
        room.setRoomInfo("이 방은 테스트 호텔의 방");
        room.setRoomName("방 이름");
        room.setRoomCount(1);
        room.setFixedMember(2);
        room.setMaxedMember(4);
        room.setAccommodation(accommodation);

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        accommodation.setRoomId(rooms);

        //when
        springDataJpaAccommodationRepository.save(accommodation);
        springDataJpaRoomRepository.save(room);
        List<Accommodation> accommodationList = springDataJpaAccommodationRepository.findAll();
        //then
        Assertions.assertThat(accommodationList).isNotEmpty();
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
        Accommodation accommodation = new Accommodation();
        accommodation.setIntroduction("소개합니다 호텔");
        accommodation.setAccommodationName("테스트 호텔");
        accommodation.setAccommodationType(AccommodationType.HOTEL);
        accommodation.setAccommodationImage("이미지 링크");

        Room room = new Room();
        room.setRoomInfo("이 방은 테스트 호텔의 방");
        room.setRoomName("방 이름");
        room.setRoomCount(1);
        room.setFixedMember(2);
        room.setMaxedMember(4);
        room.setAccommodation(accommodation);

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        accommodation.setRoomId(rooms);
        //when
        springDataJpaAccommodationRepository.save(accommodation);
        springDataJpaRoomRepository.save(room);
        Optional<Accommodation> hotel = springDataJpaAccommodationRepository.findByAccommodationType(AccommodationType.HOTEL);

        //then
        Assertions.assertThat(hotel.get().getAccommodationType()).isEqualTo(accommodation.getAccommodationType());
    }

    //호텔, 모텔 등 종류별 조회 실패
    @Test
    void 숙소종류별로조회_실패(){
        //given
        Accommodation accommodation1 = new Accommodation();
        accommodation1.setIntroduction("소개합니다 호텔");
        accommodation1.setAccommodationName("테스트 호텔");
        accommodation1.setAccommodationType(AccommodationType.HOTEL);
        accommodation1.setAccommodationImage("이미지 링크");

        Accommodation accommodation2 = new Accommodation();
        accommodation2.setIntroduction("소개합니다 모텔");
        accommodation2.setAccommodationName("테스트 모텔");
        accommodation2.setAccommodationType(AccommodationType.MOTEL);
        accommodation2.setAccommodationImage("이미지 링크");

        Room room1 = new Room();
        room1.setRoomInfo("이 방은 테스트 호텔의 방");
        room1.setRoomName("방 이름");
        room1.setRoomCount(1);
        room1.setFixedMember(2);
        room1.setMaxedMember(4);
        room1.setAccommodation(accommodation1);

        List<Room> rooms1 = new ArrayList<>();
        rooms1.add(room1);
        accommodation1.setRoomId(rooms1);

        Room room2 = new Room();
        room2.setRoomInfo("이 방은 테스트 호텔의 방");
        room2.setRoomName("방 이름");
        room2.setRoomCount(1);
        room2.setFixedMember(2);
        room2.setMaxedMember(4);
        room2.setAccommodation(accommodation1);

        List<Room> rooms2 = new ArrayList<>();
        rooms2.add(room1);
        accommodation1.setRoomId(rooms2);

        //when
        springDataJpaAccommodationRepository.save(accommodation1);
        springDataJpaRoomRepository.save(room1);

        springDataJpaAccommodationRepository.save(accommodation2);
        springDataJpaRoomRepository.save(room2);

        Optional<Accommodation> hotel = springDataJpaAccommodationRepository.findByAccommodationType(AccommodationType.HOTEL);

        //then
        Assertions.assertThat(hotel.get().getAccommodationType()).isNotEqualTo(accommodation2.getAccommodationType());
    }
}