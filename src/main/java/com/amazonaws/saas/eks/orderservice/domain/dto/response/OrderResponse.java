package com.amazonaws.saas.eks.orderservice.domain.dto.response;

import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.OrderStatus;
import com.amazonaws.saas.eks.orderservice.domain.model.lineitem.LineItem;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private String orderId;
    private String status = OrderStatus.PROCESSING.toString();
    private List<LineItem> lineItems = new ArrayList<>();
    private Customer customer;
    private double totalAmount;
    private Instant created;
    private Instant modified;
}
