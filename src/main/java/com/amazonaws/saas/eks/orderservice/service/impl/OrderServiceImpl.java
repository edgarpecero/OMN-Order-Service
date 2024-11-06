package com.amazonaws.saas.eks.orderservice.service.impl;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Counter;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
import com.amazonaws.saas.eks.orderservice.mapper.OrderMapper;
import com.amazonaws.saas.eks.orderservice.repository.CounterRepository;
import com.amazonaws.saas.eks.orderservice.repository.OrderRepository;
import com.amazonaws.saas.eks.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository repository;

    @Autowired
    private CounterRepository counterRepository;
    
    @Override
    public OrderResponse create(CreateOrderRequest request) {
        // Maps the CreateOrderRequest to an Order entity using the OrderMapper
        Order order = OrderMapper.INSTANCE.createOrderRequestToOrder(request);

        // Assigns the next sequential order number
        order.setOrderId(getOrderNumber());

        // Saves the Order entity to the repository and returns the saved Order
        Order savedOrder = repository.save(order);

        // Maps the saved Order entity to an OrderResponse and returns it
        return OrderMapper.INSTANCE.orderToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getById(String orderId) {
        return null;
    }

    @Override
    public void deleteById(String orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public OrderResponse update(String orderId, UpdateOrderRequest request) {
        return null;
    }

    @Override
    public ListOrdersResponse getAll() {
        return repository.findAll();
    }

    private String getOrderNumber() {
        Counter latestCounter = counterRepository.get(EntityType.ORDERS.getLabel());
        return "Order # - " + latestCounter.getCount();
    }
}
