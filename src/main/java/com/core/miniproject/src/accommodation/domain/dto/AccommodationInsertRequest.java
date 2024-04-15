package com.core.miniproject.src.accommodation.domain.dto;

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
    private String accommodationType;
    private String introduction;
    private String address;
    private String locationName;
    private double discountRate;

}