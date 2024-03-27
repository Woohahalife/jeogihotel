package com.core.miniproject.src.room;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.domain.entity.Discount;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.rate.domain.entity.Rate;
import com.core.miniproject.src.room.domain.dto.RoomInsertRequest;
import com.core.miniproject.src.room.domain.dto.RoomInsertResponse;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import com.core.miniproject.src.room.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;

import java.util.Collections;
import java.util.Optional;

import static com.core.miniproject.src.common.response.BaseResponseStatus.ACCOMMODATION_DOES_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    MemberInfo memberInfo = new MemberInfo(1L, "string", Role.USER);
    Accommodation accommodation = Accommodation.builder()
            .accommodationName("숙소이름")
            .accommodationType(AccommodationType.HOTEL)
            .introduction("숙소설명")
            .accommodationImage("숙소 이미지")
            .location(Location.builder().id(1L).build())
            .discount(Discount.builder().id(1L).build())
            .rates(Collections.singletonList(Rate.builder().id(1L).build()))
            .build();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        roomService = new RoomService(accommodationRepository, roomRepository);
    }

    @Test
    void room_저장_성공() {
        // given
        RoomInsertRequest request =
                new RoomInsertRequest("객실1", "객실정보1", 50, 2, 4, "이미지", 20000);

        BDDMockito.given(accommodationRepository.findById(any())).willReturn(Optional.of(accommodation));

        Room expectedRoom = Room.builder()
                .roomName(request.getRoomName())
                .roomInfo(request.getRoomInfo())
                .roomCount(request.getRoomCount())
                .fixedMember(request.getFixedNumber())
                .maxedMember(request.getMaxedNumber())
                .roomImage(request.getRoomImage())
                .accommodationId(accommodation)
                .build();

        BDDMockito.given(roomRepository.save(argThat(room -> room.getRoomName().equals("객실1"))))
                .willReturn(expectedRoom);

        // when
        RoomInsertResponse response = roomService.createRoom(accommodation.getId(), request, memberInfo);

        // then
        BDDMockito.verify(accommodationRepository).findById(accommodation.getId());
        BDDMockito.verify(roomRepository).save(argThat(room -> room.getRoomName().equals("객실1")));
        assertThat(response.getRoomName()).isEqualTo(expectedRoom.getRoomName());
    }

    @Test
    void accommodation_존재하지_않으면_예외_발생() {
        RoomInsertRequest request =
                new RoomInsertRequest("객실1", "객실정보1", 50, 2, 4, "이미지", 20000);

        BDDMockito.given(accommodationRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> roomService.createRoom(accommodation.getId(), request, memberInfo))
                .isInstanceOf(BaseException.class)
                .hasMessage(ACCOMMODATION_DOES_NOT_EXIST.getMessage());
    }
}
