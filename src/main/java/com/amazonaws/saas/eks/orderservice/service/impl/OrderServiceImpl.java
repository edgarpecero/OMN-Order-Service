package com.amazonaws.saas.eks.orderservice.service.impl;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
import com.amazonaws.saas.eks.orderservice.mapper.OrderMapper;
import com.amazonaws.saas.eks.orderservice.repository.OrderRepository;
import com.amazonaws.saas.eks.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository repository;
    
    @Override
    public OrderResponse create(CreateOrderRequest request) {
        Order order = OrderMapper.INSTANCE.createOrderRequestToOrder(request);

        Order savedOrder = repository.save(order);

        return OrderMapper.INSTANCE.orderToOrderResponse(savedOrder);
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
