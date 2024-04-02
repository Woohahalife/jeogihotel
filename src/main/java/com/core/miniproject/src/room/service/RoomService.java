package com.core.miniproject.src.room.service;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.repository.AccommodationRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.image.domain.entity.RoomImage;
import com.core.miniproject.src.image.repository.RoomImageRepository;
import com.core.miniproject.src.room.domain.dto.RoomInsertRequest;
import com.core.miniproject.src.room.domain.dto.RoomInsertResponse;
import com.core.miniproject.src.room.domain.dto.RoomRequest;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository imageRepository;

    @Transactional
    public RoomInsertResponse createRoom(
            Long accommodationId,
            RoomInsertRequest request, MemberInfo memberInfo)
    {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new BaseException(ACCOMMODATION_DOES_NOT_EXIST));

        Room room = getRoomForRequest(request, accommodation);

        room.getRoomImage().assignRoom(room);

        return RoomInsertResponse.toClient(roomRepository.save(room));
    }

    @Transactional
    public List<RoomResponse> findAllRoomByAccommodationId(Long accommodaitonId) {
        List<RoomResponse> responses = new ArrayList<>();
        List<Room> rooms = roomRepository.findAllByAccommodationId(accommodaitonId);

        for (Room room : rooms) {
            RoomResponse response = RoomResponse.toClient(room);
            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public BaseResponseStatus deleteRoom(Long accommodationId, Long roomId, MemberInfo memberInfo){
        Room room = roomRepository.findById(accommodationId,roomId).orElseThrow(
                () -> new BaseException(ROOM_NOT_FOUND)
        );
        try {
            roomRepository.deleteById(room.getId());
            return BaseResponseStatus.DELETE_SUCCESS;
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DELETE_FAIL);
        }
    }

    @Transactional
    public RoomResponse updateRoom(Long accommodationId, Long roomId, RoomRequest request, MemberInfo memberInfo){
        Room room = roomRepository.findById(accommodationId, roomId).orElseThrow(
                ()->new BaseException(ROOM_NOT_FOUND)
        );
        RoomImage image = getImageForRequest(request, room);

        room.update(request, image);
        room.getRoomImage().assignRoom(room);

        return RoomResponse.toClient(room);
    }

    private Room getRoomForRequest(RoomInsertRequest request, Accommodation accommodation) {
        numberOfPeopleValidate(request);
        requiredInfoValidate(request);
        roomPricePolicyValidate(request); // 가격 설정 정책을 지키지 못했다는 것을 따로 표시하기 위해 검증문 분리 설정

        RoomImage image = RoomImage.builder()
                .imagePath(request.getRoomImage())
                .build();

        RoomImage roomImage = imageRepository.save(image);

        return Room.builder()
                .roomName(request.getRoomName())
                .roomInfo(request.getRoomInfo())
                .roomCount(request.getRoomCount())
                .fixedMember(request.getFixedNumber())
                .maxedMember(request.getMaxedNumber())
                .roomImage(roomImage)
                .price(request.getPrice())
                .accommodationId(accommodation)
                .build();
    }

    private void requiredInfoValidate(RoomInsertRequest request) {
        if(request.getRoomImage().isEmpty() ||
           request.getRoomName().isEmpty() ||
           request.getRoomInfo().isEmpty() ||
           request.getRoomCount() <= 0) {
            throw new BaseException(SET_REQUIRED_INFORMATION);
        }
    }

    private void numberOfPeopleValidate(RoomInsertRequest request) {
        if(request.getFixedNumber() < 1 || request.getMaxedNumber() >= 10) {
            throw new BaseException(ERROR_SETTING_NUM_OF_PEOPLE);
        }

        if(request.getFixedNumber() > request.getMaxedNumber()) {
            throw new BaseException(ERROR_SETTING_NUMBER_OF_GUEST);
        }
    }

    private void roomPricePolicyValidate(RoomInsertRequest request) {
        if(request.getPrice() < 30000) {
            throw new BaseException(FAILURE_PRICING_POLICY);
        }
    }

    private RoomImage getImageForRequest (RoomRequest request, Room room){
        if(!request.getImagePath().equals(room.getRoomImage().getImagePath())){
            return RoomImage.builder()
                    .imagePath(request.getImagePath())
                    .build();
        }else{
            return room.getRoomImage();
        }
    }

}
