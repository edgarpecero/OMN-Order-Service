package com.amazonaws.saas.eks.orderservice.domain.model.converter;

import com.amazonaws.saas.eks.orderservice.domain.model.lineitem.LineItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;

public class LineItemConverter implements AttributeConverter<List<LineItem>> {

    private final ObjectMapper mapper;

    public LineItemConverter() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public AttributeValue transformFrom(List<LineItem> lineItems) {
        try {
            return EnhancedAttributeValue.fromString(mapper.writeValueAsString(lineItems)).toAttributeValue();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LineItem> transformTo(AttributeValue attributeValue) {
        try {
            return mapper.readValue(attributeValue.s(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EnhancedType<List<LineItem>> type() {
        return EnhancedType.listOf(LineItem.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}