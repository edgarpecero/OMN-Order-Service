package com.amazonaws.saas.eks.orderservice.domain.model.converter;

import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.lineitem.LineItem;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.utils.ImmutableMap;

import java.util.Map;

public final class OrderConverterProvider implements AttributeConverterProvider {
    private final Map<EnhancedType<?>, AttributeConverter<?>> converterCache = ImmutableMap.of(
            EnhancedType.of(Customer.class), new CustomerConverter(),
            EnhancedType.listOf(LineItem.class), new LineItemConverter()
    );

    @SuppressWarnings("unchecked")
    @Override
    public <T> AttributeConverter<T> converterFor(EnhancedType<T> enhancedType) {
        return (AttributeConverter<T>) converterCache.get(enhancedType);
    }
}
