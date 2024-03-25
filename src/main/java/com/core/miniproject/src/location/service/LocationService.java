package com.core.miniproject.src.location.service;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.location.domain.dto.LocationRequest;
import com.core.miniproject.src.location.domain.dto.LocationResponse;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public LocationResponse createLocation(LocationRequest request) {

        locationRepository.findLocationByType(request.getLocationName())
                .ifPresent(rate -> {
                    throw new BaseException(BaseResponseStatus.DUPLICATE_LOCATION);
                });

        Location location = Location.builder()
                .locationName(request.getLocationName())
                .build();

        return LocationResponse.toClient(locationRepository.save(location));
    }
}
