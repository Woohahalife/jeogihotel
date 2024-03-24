package com.core.miniproject.src.room.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.repository.SpringDataJpaAccommodationRepository;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.roomprice.domain.RoomPrice;
import com.core.miniproject.src.roomprice.repository.SpringDataJpaRoomPriceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SpringDataJpaRoomRepositoryTest {

    @Autowired
    SpringDataJpaAccommodationRepository springDataJpaAccommodationRepository;
    @Autowired
    SpringDataJpaRoomRepository springDataJpaRoomRepository;
    @Autowired
    SpringDataJpaRoomPriceRepository springDataJpaRoomPriceRepository;

    @Test
    void create() {

        RoomPrice roomPrice = RoomPrice.builder()
                .price(200000)
                .build();

        Room room = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(null)
                .roomPrice(roomPrice)
                .build();

        //when
        RoomPrice newRoomPrice = springDataJpaRoomPriceRepository.save(roomPrice);
        Room newRoom = springDataJpaRoomRepository.save(room);

        //then
        Assertions.assertThat(newRoom.getRoomPrice()).isEqualTo(newRoomPrice);
        Assertions.assertThat(room.getRoomPrice().getPrice()).isEqualTo(newRoom.getRoomPrice().getPrice());
    }

    @Test
    void findAll_성공() {

        RoomPrice roomPrice = RoomPrice.builder()
                .price(200000)
                .build();

        Room room = Room.builder()
                .roomName("더블 디럭스")
                .roomInfo("테스트 호텔의 객실")
                .roomCount(40)
                .fixedMember(2)
                .maxedMember(4)
                .accommodationId(null)
                .roomPrice(roomPrice)
                .build();

        springDataJpaRoomPriceRepository.save(roomPrice);
        springDataJpaRoomRepository.save(room);
        //when
        List<Room> rooms = springDataJpaRoomRepository.findAll();
        for (Room room1 : rooms) {
            System.out.println("room1.getRoomPrice().getPrice() = " + room1.getRoomPrice().getPrice());
        }
        Assertions.assertThat(rooms.size()).isEqualTo(1);
    }

    @Test
    void findAll_실패() {
        List<Room> rooms = springDataJpaRoomRepository.findAll();
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
                .build();
        Room room = Room.builder()
                .accommodationId(accommodation)
                .roomInfo("테스트 테스트")
                .build();
        RoomPrice roomPrice = RoomPrice.builder()
                .price(20000)
                .room(room)
                .build();
        //when
        springDataJpaAccommodationRepository.save(accommodation);
        springDataJpaRoomRepository.save(room);
        springDataJpaRoomPriceRepository.save(roomPrice);

        List<Room> rooms = springDataJpaRoomRepository.findAllByAccommodationId(1L);
        Assertions.assertThat(rooms.size()).isEqualTo(1);
    }


}