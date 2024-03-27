package com.core.miniproject.src.room.service;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.room.domain.dto.RoomInsertRequest;
import com.core.miniproject.src.room.domain.dto.RoomInsertResponse;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public RoomInsertResponse createRoom(
            Long accommodationId,
            RoomInsertRequest request, MemberInfo memberInfo)
    {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new BaseException(ACCOMMODATION_DOES_NOT_EXIST));

        Room room = getRoomForRequest(request, accommodation);

        return RoomInsertResponse.toClient(roomRepository.save(room));
    }

    @Transactional
    public List<RoomResponse> findAllRoomByAccommodationId(Long accommodaitonId){
        List<RoomResponse> responses = new ArrayList<>();
        List<Room> rooms = roomRepository.findAllByAccommodationId(accommodaitonId);
        for (Room room : rooms) {
            RoomResponse response = RoomResponse.toClient(room);
            responses.add(response);
        }
        return responses;
    }


    private Room getRoomForRequest(RoomInsertRequest request, Accommodation accommodation) {
        //TODO : rate 필드 추가해야함

        numberOfPeopleValidate(request);
        requiredInfoValidate(request);

        return Room.builder()
                .roomName(request.getRoomName())
                .roomInfo(request.getRoomInfo())
                .roomCount(request.getRoomCount())
                .fixedMember(request.getFixedNumber())
                .maxedMember(request.getMaxedNumber())
                .roomImage(request.getRoomImage())
                .price(request.getPrice())
                .accommodationId(accommodation)
                .build();
    }

    private void requiredInfoValidate(RoomInsertRequest request) {
        if(request.getPrice() <= 0 ||
           request.getRoomImage().isEmpty() ||
           request.getRoomName().isEmpty() ||
           request.getRoomInfo().isEmpty() ||
           request.getRoomCount() <= 0) {
            throw new BaseException(SET_REQUIRED_INFORMAION);
        }
    }

    private void numberOfPeopleValidate(RoomInsertRequest request) {

        if(request.getFixedNumber() <= 0 || request.getMaxedNumber() <= 0) {
            throw new BaseException(ERROR_SETTING_NUM_OF_PEOPLE);
        }

        if(request.getFixedNumber() > request.getMaxedNumber()) {
            throw new BaseException(ERROR_SETTING_NUMBER_OF_GUEST);
        }
    }

}
