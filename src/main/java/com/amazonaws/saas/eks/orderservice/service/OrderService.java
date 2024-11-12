package com.amazonaws.saas.eks.orderservice.service;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.ListOrdersResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;

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
    OrderResponse deleteById(String orderId);

    /**
     * Updates an Order
     * @param orderId Order ID
     * @param request {@link UpdateOrderRequest}
     * @return {@link OrderResponse}
     */
    OrderResponse update(String orderId, UpdateOrderRequest request);

    /**
     * Applies a Patch to an Order. See <a href="https://www.baeldung.com/spring-rest-json-patch">documentation</a>
     * Update LineItems
     * @param orderId Order ID
     * @param patch {@link JsonPatch}
     * @return {@link OrderResponse}
     */
    OrderResponse patch(String orderId, JsonPatch patch) throws JsonProcessingException;

    /**
     * Searches for Purchase Orders
     * @return {@link ListOrdersResponse}
     */
    ListOrdersResponse getAll();
}
