package com.amazonaws.saas.eks.orderservice.mapper;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.*;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.CustomerResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponse customerToCustomerResponse(Customer customer);

    Customer customerResponseToCustomer(CustomerResponse customer);

    Customer updateCustomerRequestToCustomer(UpdateCustomerRequest request);

    @Mapping(target="email", source="email")
    @Mapping(target="id", source="email")
    Customer createCustomerRequestToCustomer(CreateCustomerRequest request);
}
