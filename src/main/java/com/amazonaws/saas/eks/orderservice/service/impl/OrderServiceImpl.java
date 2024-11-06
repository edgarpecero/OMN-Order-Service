package com.amazonaws.saas.eks.orderservice.service.impl;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderResponse create(CreateOrderRequest request) {
        return null;
    }

    @Override
    public OrderResponse getById(String orderId) {
        return null;
    }

    @Override
    public void deleteById(String orderId) {

    }

    @Override
    public OrderResponse update(String orderId, UpdateOrderRequest request) {
        return null;
    }

    @Override
    public ListOrdersResponse getAll() {
        return null;
    }
}
