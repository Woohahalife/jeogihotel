package com.core.miniproject.src.room.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private String roomName;
    private String roomInfo;
    private int fixedMember;
    private int maxedMember;
    private String imagePath;
    private int price;
}
