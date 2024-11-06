package com.amazonaws.saas.eks.orderservice.domain.dto.request;

import java.util.ArrayList;
import java.util.List;

public class UpdateOrderRequest {
    private String orderId;
    private List<LineItemRequest> lineItems = new ArrayList<>();
}
