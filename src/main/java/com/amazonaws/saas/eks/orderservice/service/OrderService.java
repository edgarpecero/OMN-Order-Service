package com.amazonaws.saas.eks.orderservice.service;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;

public interface OrderService {

    /**
     * Creates a new Order
     * @param request {@link CreateOrderRequest}
     * @return {@link OrderResponse}
     */
    OrderResponse create(CreateOrderRequest request);

    /**
     * Retrieves a single Order by ID
     * @param orderId Order ID
     * @return {@link OrderResponse}
     */
    OrderResponse getById(String orderId);

    /**
     * Soft deletes an Order
     * @param orderId Order ID
     */
    void deleteById(String orderId);

    /**
     * Updates an Order
     * @param orderId Order ID
     * @param request {@link UpdateOrderRequest}
     * @return {@link OrderResponse}
     */
    OrderResponse update(String orderId, UpdateOrderRequest request);

    /**
     * Searches for Purchase Orders
     * @return {@link ListOrdersResponse}
     */
    ListOrdersResponse getAll();
}
