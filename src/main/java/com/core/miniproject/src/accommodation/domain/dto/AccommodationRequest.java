package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.location.domain.entity.LocationType;
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
    private String accommodationType;
    private String introduction;
    private LocationType locationType;
    private Double discount;
    private String address;
    private List<String> accommodationImage;


}
