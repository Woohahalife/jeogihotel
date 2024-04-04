package com.core.miniproject.src.room.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomInsertRequest {

    private String roomName;
    private String roomInfo;
    private Integer fixedNumber;
    private Integer maxedNumber;
    private String roomImage;
    private Integer price;

}
