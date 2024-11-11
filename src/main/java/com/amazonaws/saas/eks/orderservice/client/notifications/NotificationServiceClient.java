package com.amazonaws.saas.eks.orderservice.client.notifications;

import com.amazonaws.saas.eks.orderservice.client.notifications.dto.request.NotificationRequest;
import com.amazonaws.saas.eks.orderservice.client.notifications.dto.request.SubscribeToTopicRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notifications",
        url = "${spring.cloud.openfeign.client.config.notifications.url}")
public interface NotificationServiceClient {
    @PostMapping(value = "/publish", produces = {MediaType.APPLICATION_JSON_VALUE})
    void publishNotification(@RequestBody NotificationRequest request);

    @PostMapping(value = "/subscribe", produces = {MediaType.APPLICATION_JSON_VALUE})
    void subscribeToTopic(@RequestBody SubscribeToTopicRequest request);
}
