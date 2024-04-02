package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
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
public class AccommodationInsertRequest {

    private String accommodationName;
    private String accommodationType;
    private String introduction;
    private String address;
    private String locationName;
    private double discountRate;
    private List<String> accommodationImage;

}