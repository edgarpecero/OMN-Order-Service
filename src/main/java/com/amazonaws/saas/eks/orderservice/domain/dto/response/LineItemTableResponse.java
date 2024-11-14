package com.amazonaws.saas.eks.orderservice.domain.dto.response;

import lombok.Data;

@Data
public class LineItemTableResponse {
    private Long id;
    private String productName;
    private int quantity;
    private double price;
}
