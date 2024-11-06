package com.amazonaws.saas.eks.orderservice.domain.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListOrdersResponse {
    private List<OrderResponse> orders = new ArrayList<>();
    private long count;
}
