package com.amazonaws.saas.eks.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsSecretsConfig {

    @Value("${aws-access-key}")
    private String accessKey;

    @Value("${aws-secret-key}")
    private String secretKey;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
