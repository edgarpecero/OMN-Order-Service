package com.amazonaws.saas.eks.orderservice.domain.dto.response;

import com.amazonaws.saas.eks.orderservice.domain.model.enums.OrderStatus;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderTableResponse {
    private Long id;
    private String orderId;
    private String status = OrderStatus.PROCESSING.toString();
    private List<LineItemResponse> lineItems = new ArrayList<>();
    private CustomerTableResponse customer;
    private double totalAmount;
    private LocalDateTime created;
    private LocalDateTime modified;
}
