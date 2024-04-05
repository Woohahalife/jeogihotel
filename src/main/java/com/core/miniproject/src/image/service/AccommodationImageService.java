package com.core.miniproject.src.image.service;

import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.image.domain.dto.ImageResponse;
import com.core.miniproject.src.image.repository.AccommodationImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationImageService implements ImageService {

    private final AccommodationImageRepository imageRepository;

    @Override
    public ImageResponse allImages() {
        return null;
    }

}
