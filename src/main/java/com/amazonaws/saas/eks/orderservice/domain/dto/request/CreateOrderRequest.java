package com.amazonaws.saas.eks.orderservice.domain.dto.request;

import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private List<LineItemRequest> lineItems = new ArrayList<>();
    private CreateCustomerRequest customer;
}
