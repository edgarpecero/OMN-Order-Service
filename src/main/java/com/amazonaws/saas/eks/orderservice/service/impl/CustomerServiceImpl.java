package com.amazonaws.saas.eks.orderservice.service.impl;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateCustomerRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.UpdateCustomerRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.CustomerResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.mapper.CustomerMapper;
import com.amazonaws.saas.eks.orderservice.repository.CustomerRepository;
import com.amazonaws.saas.eks.orderservice.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Override
    public CustomerResponse create(CreateCustomerRequest request) {
        Customer customer = CustomerMapper.INSTANCE.createCustomerRequestToCustomer(request);
        return CustomerMapper.INSTANCE.customerToCustomerResponse(repository.save(customer));
    }

    @Override
    public Customer getById(String customerId) {
        return null;
    }

    @Override
    public CustomerResponse deleteById(String customerId) {
        return null;
    }

    @Override
    public CustomerResponse update(String customerId, UpdateCustomerRequest request) {
        return null;
    }

    @Override
    public CustomerResponse patch(String customerId, JsonPatch patch) throws JsonProcessingException {
        return null;
    }
}
