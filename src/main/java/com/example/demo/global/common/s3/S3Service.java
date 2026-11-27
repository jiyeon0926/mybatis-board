package com.example.demo.global.common.s3;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class S3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);

    private final S3Template s3Template;

    public S3Service(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    public void uploadFile(String bucketName, String key, InputStream is, String contentType) {
        s3Template.upload(
                bucketName,
                key,
                is,
                ObjectMetadata.builder().contentType(contentType).build()
        );

        log.info("S3 파일 업로드 완료: {}", key);
    }
}
