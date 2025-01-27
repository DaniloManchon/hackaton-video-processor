package com.fiap.video_processor.aws;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class AwsProperties {
    private String access;
    private String secret;
    private String accountNo;

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    private S3Properties s3;

    @Data
    public static class S3Properties {
        private String bucket;
        private String region;
    }
}
