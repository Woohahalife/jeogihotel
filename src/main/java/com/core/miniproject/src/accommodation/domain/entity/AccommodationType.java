package com.core.miniproject.src.accommodation.domain.entity;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;

public enum AccommodationType {
    HOTEL("호텔"),
    MOTEL("모텔"),
    RESORT("리조트"),
    PENSION("펜션"),
    CAMPING("캠핑"),
    GUESTHOUSE("게스트 하우스");

    private final String type;

    AccommodationType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static AccommodationType getByText(String text) {
        for (AccommodationType type : AccommodationType.values()) {
            if (type.getType().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new BaseException(BaseResponseStatus.TYPE_MISMATCH);
    }
}
