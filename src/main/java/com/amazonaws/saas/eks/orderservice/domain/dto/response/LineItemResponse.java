package com.amazonaws.saas.eks.orderservice.domain.dto.response;

import lombok.Data;

@Data
public class LineItemResponse {
    private String id;
    private String name;
    private int quantity;
    private double price;
}
