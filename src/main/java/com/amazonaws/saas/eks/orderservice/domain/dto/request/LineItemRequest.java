package com.amazonaws.saas.eks.orderservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemRequest {
    private String productId;
    private String name;
    private int quantity;
    private double price;
}
