package com.amazonaws.saas.eks.orderservice.domain.model.lineitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineItem {
    private String id;
    private String name;
    private int quantity;
    private double price;
}
