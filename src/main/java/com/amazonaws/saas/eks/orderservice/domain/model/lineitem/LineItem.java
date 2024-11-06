package com.amazonaws.saas.eks.orderservice.domain.model.lineitem;

import lombok.Data;

@Data
public class LineItem {
    private String id;
    private String name;
    private int quantity;
    private double price;
}
