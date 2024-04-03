package com.core.miniproject.src.location.domain.dto;

import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponse {

    private String locationName;

    public static LocationResponse toClient(Location location){
        return LocationResponse.builder()
                .locationName(location.getLocationName().getType())
                .build();
    }
}
