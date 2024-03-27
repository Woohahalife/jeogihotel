package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.room.domain.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRequest {

    private String accommodationName;
    private AccommodationType accommodationType;
    private String accommodationImage;
    private String introduction;

}
