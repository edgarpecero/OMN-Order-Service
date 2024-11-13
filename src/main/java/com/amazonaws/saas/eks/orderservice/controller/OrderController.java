package com.amazonaws.saas.eks.orderservice.controller;

import com.amazonaws.saas.eks.orderservice.config.AwsSecretsConfig;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderTableRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersTableResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderTableResponse;
import com.amazonaws.saas.eks.orderservice.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
    @Autowired
    private AwsSecretsConfig awsSecretsConfig;
    @Autowired
    private OrderService service;

    @PostMapping("/orders")
    public OrderTableResponse createOrder(@RequestBody CreateOrderTableRequest request) {
        try {
            return service.save(request);
        } catch (Exception e) {
            LOGGER.error("Failed to create order", e);
            throw e;
        }
    }

    @GetMapping(value = "/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ListOrdersTableResponse findAll() {
        try {
            return service.findAll();
        } catch (Exception e) {
            LOGGER.error("Error listing orders", e);
            throw e;
        }
    }

    @DeleteMapping(value = "/orders/{orderId}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public void deleteOrder(@PathVariable Long orderId) {
        try {
            service.deleteById(orderId);
        } catch (Exception e) {
            LOGGER.error("Error deleting Order. %s", e);
            throw e;
        }
    }

    @GetMapping(value = "/orders/{orderId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Optional<OrderTableResponse> findById(@PathVariable Long orderId) {
        try {
            return service.findById(orderId);
        } catch (Exception e) {
            LOGGER.error("Error fetching order with id: " + orderId, e);
            throw e;
        }
    }

    @RequestMapping("/health")
    public String health() {
        return "\"Order service is up!\"";
    }
}
