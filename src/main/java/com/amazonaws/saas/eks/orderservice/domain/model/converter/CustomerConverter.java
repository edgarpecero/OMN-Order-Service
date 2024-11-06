package com.amazonaws.saas.eks.orderservice.domain.model.converter;

import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class CustomerConverter implements AttributeConverter<Customer> {

    private final ObjectMapper mapper;

    public CustomerConverter() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public AttributeValue transformFrom(Customer customerInfo) {
        try {
            return EnhancedAttributeValue.fromString(mapper.writeValueAsString(customerInfo)).toAttributeValue();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer transformTo(AttributeValue attributeValue) {
        try {
            return mapper.readValue(attributeValue.s(), Customer.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EnhancedType<Customer> type() {
        return EnhancedType.of(Customer.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
