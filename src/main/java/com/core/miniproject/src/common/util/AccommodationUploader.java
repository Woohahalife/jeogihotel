package com.core.miniproject.src.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationUploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 변환한 후 S3에 업로드
    public List<String> upload(List<MultipartFile> multipartFile, String dirName) throws IOException {
        List<File> uploadFiles = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        return uploadFileList(uploadFiles, dirName);
    }

    private List<String> uploadFileList(List<File> uploadFiles, String dirName) {
        List<String> fileNames = uploadFiles.stream()
                .map(file -> dirName + "/" + file.getName())
                .collect(Collectors.toList());

        List<String> uploadImageUrls = putS3(uploadFiles, fileNames);

        removeNewFiles(uploadFiles); // convert()함수로 인해서 로컬에 생성된 File 삭제

        return uploadImageUrls;
    }

    private List<String> putS3(List<File> uploadFiles, List<String> fileNames) {
        List<String> uploadedUrls = new ArrayList<>();

        for (int i = 0; i < uploadFiles.size(); i++) {
            File file = uploadFiles.get(i);
            String fileName = fileNames.get(i);
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, file)
                            .withCannedAcl(CannedAccessControlList.PublicRead) // PublicRead 권한으로 업로드 됨
            );
            String uploadedUrl = amazonS3Client.getUrl(bucket, fileName).toString();
            uploadedUrls.add(uploadedUrl);
        }
        return uploadedUrls; // 업로드된 이미지의 url
    }

    private void removeNewFiles(List<File> uploadFiles) {
        for (File file : uploadFiles) {
            if (file.delete()) {
                log.info(file.getName() + " 파일이 삭제되었습니다.");
            } else {
                log.info(file.getName() + " 파일이 삭제되지 않았습니다.");
            }
        }
    }
    private Optional<List<File>> convert(List<MultipartFile> files) throws IOException {
        List<File> convertedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uuidFilename = UUID.randomUUID().toString() + extension;

            File convertFile = new File(uuidFilename);

            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                convertedFiles.add(convertFile); // 변환된 파일을 리스트에 추가
            }
        }

        if (!convertedFiles.isEmpty()) {
            return Optional.of(convertedFiles);
        }
        return Optional.empty();
    }
}
