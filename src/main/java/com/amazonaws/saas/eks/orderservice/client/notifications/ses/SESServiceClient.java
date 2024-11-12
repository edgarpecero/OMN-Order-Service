package com.amazonaws.saas.eks.orderservice.client.notifications.ses;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notifications",
        url = "${spring.cloud.openfeign.client.config.notifications.ses.url}")
public interface SESServiceClient {
    @GetMapping("/register-email")
    public String registerEmail(@RequestParam String email);

    @GetMapping("/send-email")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body);
}
