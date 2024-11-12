package com.amazonaws.saas.eks.orderservice.domain.model.enums;

import lombok.Getter;

@Getter
public enum EntityType {
    ORDERS("ORDERS"),
    CUSTOMER("CUSTOMER"),
    COUNTER("COUNTER");

    private final String label;

    EntityType(String label) {
        this.label = label;
    }
}
