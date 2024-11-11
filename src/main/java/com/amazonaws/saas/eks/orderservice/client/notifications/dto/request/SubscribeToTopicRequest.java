package com.amazonaws.saas.eks.orderservice.client.notifications.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
@NotBlank
@Builder
public class SubscribeToTopicRequest {
    private String email;
    private String phoneNumber;
    //private String topicArn
}