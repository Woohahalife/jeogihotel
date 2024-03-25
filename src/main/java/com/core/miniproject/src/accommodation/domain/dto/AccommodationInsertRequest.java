package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationInsertRequest {

    private String accommodationName;
    private AccommodationType accommodationType;
    private String accommodationImage;
    private String introduction;
    private int price;
    private LocationType location;

}
