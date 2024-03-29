package com.core.miniproject.src.room.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.room.domain.entity.Room;
import jakarta.persistence.Column;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RoomRepositoryTest {

    @Autowired
    AccommodationRepository AccommodationRepository;
    @Autowired
    RoomRepository roomRepository;

    @Test
    void create() {

        Room room = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(null)
                .price(200000)
                .build();

        //when
        Room newRoom = roomRepository.save(room);

        //then
//        Assertions.assertThat(newRoom.getRoomPrice()).isEqualTo(roomPrice);
        Assertions.assertThat(room.getPrice()).isEqualTo(newRoom.getPrice());
    }

    @Test
    void findAll_성공() {
        Room room = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(null)
                .price(200000)
                .build();

        roomRepository.save(room);



        //when
        List<Room> rooms = roomRepository.findAll();
        for (Room room1 : rooms) {
            System.out.println("room1.getRoomPrice().getPrice() = " + room1.getPrice());
        }
        Assertions.assertThat(rooms.size()).isEqualTo(1);
    }

    @Test
    void findAll_실패() {
        List<Room> rooms = roomRepository.findAll();
        //then
        Assertions.assertThat(rooms).isEmpty();
    }

    @Test
    void findAllByAccommodationId_성공(){
        //given
        //Location->Accommodation->Room->RoomPrice
        Accommodation accommodation = Accommodation.builder()
                .accommodationName("테스트 호텔")
                .accommodationType(AccommodationType.HOTEL)
                .discount(Discount.builder().id(1L).build())
                .location(Location.builder().id(1L).build())
                .build();
        Room room = Room.builder()
                .accommodationId(accommodation)
                .roomInfo("테스트 테스트")
                .price(220000)
                .build();

        //when
        AccommodationRepository.save(accommodation);
        roomRepository.save(room);

        List<Room> rooms = roomRepository.findAllByAccommodationId(accommodation.getId());

        Assertions.assertThat(rooms.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    void 삭제_성공_조회_실패(){
        Accommodation accommodation = Accommodation.builder()
                .accommodationName("테스트 호텔")
                .accommodationType(AccommodationType.HOTEL)
                .discount(Discount.builder().id(1L).build())
                .location(Location.builder().id(1L).build())
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
        AccommodationRepository.save(accommodation);
        Room room1 = roomRepository.save(room);
        roomRepository.deleteById(room1.getId());
        Room room2 = roomRepository.findById(room1.getId()).orElse(null);

        Assertions.assertThat(room2).isNull();

    }
}