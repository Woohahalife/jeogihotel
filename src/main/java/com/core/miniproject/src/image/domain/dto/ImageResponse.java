package com.core.miniproject.src.image.domain.dto;

import com.core.miniproject.src.image.domain.entity.AccommodationImage;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private Long id;
    private String imagePath;

    public static ImageResponse toClient(AccommodationImage image) {
        return ImageResponse.builder()
                .id(image.getId())
                .imagePath(image.getImagePath())
                .build();
    }
}
