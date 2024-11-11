package com.amazonaws.saas.eks.orderservice.service.impl;

import com.amazonaws.saas.eks.orderservice.client.notifications.NotificationServiceClient;
import com.amazonaws.saas.eks.orderservice.client.notifications.dto.request.NotificationRequest;
import com.amazonaws.saas.eks.orderservice.client.notifications.dto.request.SubscribeToTopicRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.OrderStatus;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Counter;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
import com.amazonaws.saas.eks.orderservice.mapper.OrderMapper;
import com.amazonaws.saas.eks.orderservice.repository.CounterRepository;
import com.amazonaws.saas.eks.orderservice.repository.OrderRepository;
import com.amazonaws.saas.eks.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository repository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private NotificationServiceClient notificationClient;
    
    @Override
    public OrderResponse create(CreateOrderRequest request) {
        // Maps the CreateOrderRequest to an Order entity using the OrderMapper
        Order order = OrderMapper.INSTANCE.createOrderRequestToOrder(request);

        // Assigns the next sequential order number
        String orderNumber = getOrderNumber();
        order.setOrderId(String.format("ORDER%s", orderNumber));
        order.getCustomer().setCustomerId(String.format("CUSTOMER%s", orderNumber));

        // Saves the Order entity to the repository and returns the saved Order
        Order savedOrder = repository.save(order);
        subscribeToTopic(order.getCustomer());
        publishNotification(order, OrderStatus.PROCESSING);

        // Maps the saved Order entity to an OrderResponse and returns it
        return OrderMapper.INSTANCE.orderToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getById(String orderId) {
        Order order = getOrderById(orderId);
        return OrderMapper.INSTANCE.orderToOrderResponse(order);
    }

    @Override
    public void deleteById(String orderId) {
        Order order = getOrderById(orderId);
        repository.deleteById(orderId);
        publishNotification(order, OrderStatus.CANCELLED);
    }

    @Override
    public OrderResponse update(String orderId, UpdateOrderRequest request) {
        Order order = OrderMapper.INSTANCE.updateOrderRequestToOrder(request);
        order.setId(orderId);
        Order orderUpdated = repository.update(order);
        publishNotification(order, OrderStatus.valueOf(request.getStatus()));
        return OrderMapper.INSTANCE.orderToOrderResponse(orderUpdated);
    }

    @Override
    public OrderResponse patch(String orderId, JsonPatch patch) throws JsonProcessingException {
        return null;
    }

    @Override
    public ListOrdersResponse getAll() {
        return repository.findAll();
    }

    private Order getOrderById(String orderId) {
        Order order = repository.findById(orderId);
        if (order == null) {
            throw new RuntimeException(String.format("The order '%s' doesn't exist.", orderId));
        }
        return order;
    }

    private String getOrderNumber() {
        Counter latestCounter = counterRepository.get(EntityType.ORDERS.getLabel());
        return "Order # - " + latestCounter.getCount();
    }

    private void subscribeToTopic(Customer customer) {
        try {
            SubscribeToTopicRequest request = SubscribeToTopicRequest.builder().email(customer.getEmail()).build();
            notificationClient.subscribeToTopic(request);
        } catch (Exception e) {
            String message = "Failed to subscribe";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private void publishNotification(Order order, OrderStatus status) {
        try {
            NotificationRequest request = NotificationRequest.builder()
                    .email(order.getCustomer().getEmail())
                    .protocol("email")
                    .subject(String.format("Updates on %s", order.getOrderId()))
                    .message(String.format("%s has been %s.", order.getOrderId(), status.toString()))
                    .build();
            notificationClient.publishNotification(request);
        } catch (Exception e) {
            String message = "Failed to publish new notification";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
    }
}
