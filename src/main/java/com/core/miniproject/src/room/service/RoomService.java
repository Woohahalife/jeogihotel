package com.core.miniproject.src.room.service;

import com.core.miniproject.src.room.domain.dto.RoomResponse;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomResponse> findAllRoomByAccommodationId(Long accommodaitonId){
        List<RoomResponse> responses = new ArrayList<>();
        List<Room> rooms = roomRepository.findAllByAccommodationId(accommodaitonId);
        for (Room room : rooms) {
            RoomResponse response = RoomResponse.toClient(room);
            responses.add(response);
        }
        return responses;
    }
}
