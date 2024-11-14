package com.amazonaws.saas.eks.orderservice.domain.dto.response;

import lombok.Data;

@Data
public class CustomerTableResponse {
    private Long id;
    private String customerId;
    private String name;
    private String email;
}
