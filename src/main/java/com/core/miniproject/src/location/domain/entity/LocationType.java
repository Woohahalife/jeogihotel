package com.core.miniproject.src.location.domain.entity;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;

public enum LocationType {
    SEOUL("서울"),
    BUSAN("부산"),
    JEJU("제주"),
    GANGNEUNG("강릉"),
    INCHEON("인천"),
    GYEONGJU("경주");

    private final String type;

    LocationType(String type){this.type = type;}

    public String getType(){
        return type;
    }

    public static LocationType getByText(String text){
        for(LocationType type: LocationType.values()){
            if(type.getType().equalsIgnoreCase(text)){
                return type;
            }
        }
        throw new BaseException(BaseResponseStatus.LOCATION_NOT_FOUND);
    }
}
