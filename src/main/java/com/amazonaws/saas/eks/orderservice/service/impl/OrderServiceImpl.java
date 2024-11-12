package com.amazonaws.saas.eks.orderservice.service.impl;

import com.amazonaws.saas.eks.orderservice.client.notifications.ses.SESServiceClient;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateCustomerRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.OrderStatus;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
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

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private static final String ORDER_PREFIX = "ORDER";
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
    public OrderResponse create(CreateOrderRequest request) {
        // Maps the CreateOrderRequest to an Order entity using the OrderMapper
        Order order = OrderMapper.INSTANCE.createOrderRequestToOrder(request);

        // Retrieves the latest order counter and assigns the next sequential order number
        Long latestCounterNumber = getLatestCounterNumber();
        order.setOrderId(String.format("%s%s", ORDER_PREFIX, latestCounterNumber));

        // Checks if the customer already exists in the repository by their email
        Customer customer = customerRepository.findById(request.getCustomer().getEmail());
        if (customer == null) {
            // Creates a new Customer entity if the customer does not exist
            order.setCustomer(createCustomer(request.getCustomer(), latestCounterNumber));
            // Registers the new customer's email for notifications
            registerEmail(order.getCustomer());
        } else {
            // Sets the existing customer to the order if found
            order.setCustomer(customer);
        }

        // Calculates the total amount for the order based on line items
        calculateTotalAmount(order);

        // Saves the Order entity to the repository and returns the saved Order
        Order savedOrder = repository.save(order);

        // Sends a notification email regarding the new order
        sendEmail(order);

        // Maps the saved Order entity to an OrderResponse and returns it
        return OrderMapper.INSTANCE.orderToOrderResponse(savedOrder);
    }


    @Override
    public OrderResponse getById(String orderId) {
        Order order = getOrderById(orderId);
        return OrderMapper.INSTANCE.orderToOrderResponse(order);
    }

    @Override
    public OrderResponse deleteById(String orderId) {
        //TODO: We want to update status to cancelled. No delete it from table.
//        Order order = getOrderById(orderId);
//        repository.deleteById(orderId);

        UpdateOrderRequest request = UpdateOrderRequest.builder()
                .id(orderId)
                .status(OrderStatus.CANCELLED.toString())
                .build();
        return update(orderId, request);
    }

    @Override
    public OrderResponse update(String orderId, UpdateOrderRequest request) {
        Order order = OrderMapper.INSTANCE.updateOrderRequestToOrder(request);
        order.setId(orderId);
        Order orderUpdated = repository.update(order);
        sendEmail(orderUpdated);
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

    private void calculateTotalAmount(Order order) {
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

    private void registerEmail(Customer customer) {
        try {
            sesServiceClient.registerEmail(customer.getId());
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
