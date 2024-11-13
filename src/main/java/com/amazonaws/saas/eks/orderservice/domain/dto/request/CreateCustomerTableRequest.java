package com.amazonaws.saas.eks.orderservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerTableRequest {
    private Long id;
    private String customerId;
    private String name;
    private String email;
}
