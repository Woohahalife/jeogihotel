package com.core.miniproject.src.room.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.roomprice.domain.RoomPrice;
import com.core.miniproject.src.roomprice.repository.RoomPriceRepository;
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
class RoomRepositoryTest {

    @Autowired
    AccommodationRepository AccommodationRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomPriceRepository roomPriceRepository;

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
        RoomPrice newRoomPrice = roomPriceRepository.save(roomPrice);
        Room newRoom = roomRepository.save(room);

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

        roomPriceRepository.save(roomPrice);
        roomRepository.save(room);
        //when
        List<Room> rooms = roomRepository.findAll();
        for (Room room1 : rooms) {
            System.out.println("room1.getRoomPrice().getPrice() = " + room1.getRoomPrice().getPrice());
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
        AccommodationRepository.save(accommodation);
        roomRepository.save(room);
        roomPriceRepository.save(roomPrice);

        List<Room> rooms = roomRepository.findAllByAccommodationId(accommodation.getId());

        Assertions.assertThat(rooms.size()).isEqualTo(1);
    }


}