package com.amazonaws.saas.eks.orderservice.service;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderTableRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderTableRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersTableResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderTableResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.order.OrderTable;

import java.util.Optional;

public interface OrderService {

    /**
     * Saves a new order.
     * @param request Order data to be saved
     * @return {@link OrderResponse} Saved order details
     */
    OrderTableResponse save(CreateOrderTableRequest request);

    /**
     * Retrieves a single Order by ID.
     * @param orderId Order ID
     * @return {@link OrderResponse} Order details
     */
    Optional<OrderTableResponse>  findById(Long orderId);

    /**
     * Retrieves all orders.
     * @return {@link ListOrdersTableResponse} List of all orders
     */
    ListOrdersTableResponse findAll();

    /**
     * Deletes an Order by ID.
     * @param orderId Order ID
     */
    void deleteById(Long orderId);

    /**
     * Updates an existing Order.
     * @param orderId Order ID to be updated
     * @param request New order details
     * @return {@link OrderResponse} Updated order details
     */
    OrderTableResponse updateOrder(Long orderId, UpdateOrderTableRequest request);
}

