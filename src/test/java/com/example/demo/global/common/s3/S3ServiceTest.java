package com.example.demo.global.common.s3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S3ServiceTest {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private S3Client s3Client;

    @DisplayName("AWS S3에 파일을 업로드합니다.")
    @Test
    void uploadFile() {
        // given
        String bucketName = "test-651706767480-ap-northeast-2-an";

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = date + "/" + UUID.randomUUID() + ".txt";

        byte[] content = "AWS S3 업로드 테스트".getBytes();
        ByteArrayInputStream is = new ByteArrayInputStream(content);

        // when
        s3Service.uploadFile(bucketName, key, is, "text/plain");

        // then
        HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        HeadObjectResponse response = s3Client.headObject(request);

        assertThat(response.contentLength()).isEqualTo(content.length);
        assertThat(response.contentType()).isEqualTo("text/plain");
    }
}