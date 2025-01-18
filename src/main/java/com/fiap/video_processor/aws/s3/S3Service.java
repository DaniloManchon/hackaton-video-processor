package com.fiap.video_processor.aws.s3;

import org.springframework.beans.factory.annotation.Qualifier;
import software.amazon.awssdk.services.s3.S3Client;

public class S3Service {
    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    public S3Service(@Qualifier("s3Client") S3Client s3Client, AwsProperties awsProperties) {
        this.s3Client = s3Client;
        this.awsProperties = awsProperties;
    }

}
//https://medium.com/codex/aws-s3-in-spring-boot-ca4b173e9cb1