package com.amazonaws.saas.eks.orderservice.service.impl;

import com.amazonaws.saas.eks.orderservice.client.notifications.ses.SESServiceClient;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.*;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersTableResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderTableResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.CustomerTable;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.OrderStatus;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
import com.amazonaws.saas.eks.orderservice.domain.model.order.OrderTable;
import com.amazonaws.saas.eks.orderservice.mapper.CustomerMapper;
import com.amazonaws.saas.eks.orderservice.mapper.OrderMapper;
import com.amazonaws.saas.eks.orderservice.repository.CounterRepository;
import com.amazonaws.saas.eks.orderservice.repository.CustomerRepository;
import com.amazonaws.saas.eks.orderservice.repository.OrderRepository;
import com.amazonaws.saas.eks.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private static final String ORDER_PREFIX = "OMN";
    private static final String CUSTOMER_PREFIX = "CUSTOMER";
    @Autowired
    private OrderRepository repository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SESServiceClient sesServiceClient;

    @Override
    public OrderTableResponse save(CreateOrderTableRequest request) {
        OrderTable order = OrderMapper.INSTANCE.createOrderTableRequestToOrderTable(request);
        calculateTotalAmount(order);

        registerEmail(order.getCustomer());

        OrderTable savedOrder = repository.save(order);

        return OrderMapper.INSTANCE.orderTableToOrderTableResponse(savedOrder);
    }

    @Override
    public Optional<OrderTableResponse> findById(Long orderId) {
        Optional<OrderTable> order = repository.findById(orderId);
        System.out.println(order);
        return order.map(OrderMapper.INSTANCE::orderTableToOrderTableResponse);
    }

    @Override
    public ListOrdersTableResponse findAll() {
        List<OrderTable> orders = repository.findAll();
        List<OrderTableResponse> orderResponses = orders.stream()
                .map(OrderMapper.INSTANCE::orderTableToOrderTableResponse)
                .collect(Collectors.toList());

        ListOrdersTableResponse response = new ListOrdersTableResponse();
        response.setOrders(orderResponses);
        response.setCount(orderResponses.size());

        return response;
    }

    public void deleteById(Long orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public OrderTableResponse updateOrder(Long orderId, UpdateOrderTableRequest request) {
        Optional<OrderTable> orderTableOptional = repository.findById(orderId);
        if (orderTableOptional.isPresent()) {
            OrderTable orderTable = orderTableOptional.get();
            orderTable.setOrderId(request.getOrderId());
            orderTable.setStatus(request.getStatus());
            calculateTotalAmount(orderTable);

            OrderTable updatedOrder = repository.save(orderTable);
            return OrderMapper.INSTANCE.orderTableToOrderTableResponse(updatedOrder);
        } else {
            throw new RuntimeException("Order not found for update");
        }
    }

    private void calculateTotalAmount(OrderTable order) {
        double totalAmount = order.getLineItems().stream()
                .mapToDouble(l -> l.getQuantity() * l.getPrice())
                .sum();
        order.setTotalAmount(totalAmount);
    }

    private Long getLatestCounterNumber() {
        return counterRepository.get(EntityType.ORDERS.getLabel()).getCount();
    }

    private Customer createCustomer(CreateCustomerRequest request, Long latestCounterNumber) {
        String customerId = String.format("%s%s", CUSTOMER_PREFIX, latestCounterNumber);
        Customer customer = CustomerMapper.INSTANCE.createCustomerRequestToCustomer(request);
        customer.setCustomerId(customerId);
        return customerRepository.save(customer);
    }

    private void registerEmail(CustomerTable customer) {
        try {
            sesServiceClient.registerEmail(customer.getEmail());
        } catch (Exception e) {
            String message = "Failed to register customer's email.";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private void sendEmail(Order order) {
        try {
            String message = String.format("Dear Customer, \n\nWe wanted to inform you that the status of your order #%s has been updated. The current status is: %s.\n\nThank you for choosing us.", order.getOrderId(), order.getStatus());
            String subject = String.format("Order #%s Status Update: %s", order.getOrderId(), order.getStatus());

            sesServiceClient.sendEmail(order.getCustomer().getId(), subject, message);
        } catch (Exception e) {
            String message = "Failed to publish new notification";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
    }
}
