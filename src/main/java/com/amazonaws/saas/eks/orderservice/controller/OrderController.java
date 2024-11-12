package com.amazonaws.saas.eks.orderservice.controller;

import com.amazonaws.saas.eks.orderservice.config.AwsSecretsConfig;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
    @Autowired
    private AwsSecretsConfig awsSecretsConfig;
    @Autowired
    private OrderService service;
    @GetMapping("/")
    public String sayHello() {
        System.out.println(awsSecretsConfig.getAccessKey());
        return "Hello, World!\nThis is Edgar Pecero's order-service-endpoint.";
    }

    @PostMapping("/orders")
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        try {
            return service.create(request);
        } catch (Exception e) {
            LOGGER.error("Failed to create order", e);
            throw e;
        }
    }

    @GetMapping(value = "/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ListOrdersResponse getAll() {
        try {
            return service.getAll();
        } catch (Exception e) {
            LOGGER.error("Error listing orders", e);
            throw e;
        }
    }

    @DeleteMapping(value = "/orders/{orderId}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public OrderResponse deleteOrder(@PathVariable String orderId) {
        try {
            return service.deleteById(orderId);
        } catch (Exception e) {
            LOGGER.error("Error deleting Order. %s", e);
            throw e;
        }
    }

}
