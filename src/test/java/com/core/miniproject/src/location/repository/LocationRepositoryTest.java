package com.core.miniproject.src.location.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void 지역_정보_생성() {

        // given
        Location location = Location.builder()
                .accommodation(Collections.singletonList(Accommodation.builder().id(1L).build()))
                .locationName(LocationType.SEOUL)
                .build();

        // when
        Location save = locationRepository.save(location);

        // then
        assertThat(save.getAccommodation().size()).isEqualTo(1);
        assertThat(save.getId()).isEqualTo(location.getId());
        assertThat(save.getLocationName()).isEqualTo(LocationType.SEOUL);
    }

    @Test
    void 지역_타입_기반_지역_정보_출력() {
        // given
        Location location = Location.builder()
                .accommodation(Collections.singletonList(Accommodation.builder().id(1L).build()))
                .locationName(LocationType.SEOUL)
                .build();

        Location savedLocation = locationRepository.save(location);

        // when
        Location locationByType = locationRepository.findLocationByType(LocationType.SEOUL)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LOCATION_NOT_FOUND));

        // then
        assertThat(locationByType.getId()).isEqualTo(savedLocation.getId());
        assertThat(locationByType.getLocationName()).isEqualTo(savedLocation.getLocationName());
    }
}
