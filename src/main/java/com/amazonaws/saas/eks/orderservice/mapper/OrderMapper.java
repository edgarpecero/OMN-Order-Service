package com.amazonaws.saas.eks.orderservice.mapper;

import com.amazonaws.saas.eks.orderservice.domain.dto.request.*;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderTableResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.lineitem.LineItem;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
import com.amazonaws.saas.eks.orderservice.domain.model.order.OrderTable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResponse orderToOrderResponse(Order order);

    Order updateOrderRequestToOrder(UpdateOrderRequest request);

    Order createOrderRequestToOrder(CreateOrderRequest request);

    LineItem lineItemRequestToLineItem(LineItemRequest lineItemRequest);

    //JPA implementation
    OrderTableResponse orderTableToOrderTableResponse(OrderTable order);
    OrderTable createOrderTableRequestToOrderTable(CreateOrderTableRequest request);
    OrderTable createOrderTableRequestToOrderTable(UpdateOrderTableRequest request);
}
