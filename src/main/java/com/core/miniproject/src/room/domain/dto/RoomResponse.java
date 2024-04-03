package com.core.miniproject.src.room.domain.dto;


import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.room.domain.entity.Room;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {

    private Long id;
    private String roomName;
    private String roomInfo;
    private int fixedMember;
    private int maxedMember;
    private String roomImage;
    private int price;

    public static RoomResponse toClient(Room room){
        return RoomResponse.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .roomInfo(room.getRoomInfo())
                .fixedMember(room.getFixedMember())
                .maxedMember(room.getMaxedMember())
                .price(room.getPrice())
                .roomImage(room.getRoomImage().getImagePath())
                .build();
    }
}
