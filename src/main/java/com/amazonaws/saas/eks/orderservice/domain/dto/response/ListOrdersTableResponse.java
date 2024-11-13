package com.amazonaws.saas.eks.orderservice.domain.dto.response;

import com.amazonaws.saas.eks.orderservice.domain.model.order.OrderTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListOrdersTableResponse {
    private List<OrderTableResponse> orders = new ArrayList<>();
    private long count;
}
