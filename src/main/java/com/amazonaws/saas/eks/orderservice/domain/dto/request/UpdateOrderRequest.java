package com.amazonaws.saas.eks.orderservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderRequest {
    private String id;
    private String orderId;
    private String status;
    private List<LineItemRequest> lineItems = new ArrayList<>();
}
