package com.amazonaws.saas.eks.orderservice.client.notifications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notifications",
        url = "${spring.cloud.openfeign.client.config.notifications.url}")
public interface NotificationServiceClient {
    @PostMapping("/publish")
    void publishNotification(@RequestBody String message, @RequestParam String subject);

    @PostMapping("/subscribe")
    void subscribeToTopic(@RequestBody String endpoint);
}
