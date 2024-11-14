package com.amazonaws.saas.eks.orderservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderTableRequest {
    private List<LineItemTableRequest> lineItems = new ArrayList<>();
    private CreateCustomerTableRequest customer;
}
