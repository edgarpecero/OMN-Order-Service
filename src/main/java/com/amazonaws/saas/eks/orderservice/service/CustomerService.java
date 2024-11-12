package com.amazonaws.saas.eks.orderservice.service;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateCustomerRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateCustomerRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.CustomerResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;

public interface CustomerService {

    /**
     * Creates a new Customer
     * @param request {@link CreateCustomerRequest}
     * @return {@link CustomerResponse}
     */
    CustomerResponse create(CreateCustomerRequest request);

    /**
     * Retrieves a single Customer by ID
     * @param customerId Customer ID
     * @return {@link CustomerResponse}
     */
    Customer getById(String customerId);

    /**
     * Soft deletes a Customer
     * @param customerId Customer ID
     * @return {@link CustomerResponse}
     */
    CustomerResponse deleteById(String customerId);

    /**
     * Updates a Customer
     * @param customerId Customer ID
     * @param request {@link UpdateCustomerRequest}
     * @return {@link CustomerResponse}
     */
    CustomerResponse update(String customerId, UpdateCustomerRequest request);

    /**
     * Applies a Patch to a Customer. See <a href="https://www.baeldung.com/spring-rest-json-patch">documentation</a>
     * Update Customer details
     * @param customerId Customer ID
     * @param patch {@link JsonPatch}
     * @return {@link CustomerResponse}
     */
    CustomerResponse patch(String customerId, JsonPatch patch) throws JsonProcessingException;
}
