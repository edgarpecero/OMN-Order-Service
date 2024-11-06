package com.amazonaws.saas.eks.orderservice.repository;

import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.time.Instant;
import java.util.UUID;

@Repository
public class OrderRepository {
    private static final Logger LOGGER = LogManager.getLogger(OrderRepository.class);

    private final DynamoDbTable<Order> table;

    public OrderRepository(DynamoDbEnhancedClient client) {
        this.table = client.table(Order.TABLE, TableSchema.fromBean(Order.class));
    }

    public Order save(Order order) {
        populateIds(order);
        if (order.getCreated() == null) {
            order.setCreated(Instant.now());
        }
        if (order.getModified() == null) {
            order.setModified(Instant.now());
        }
        try {
            table.putItem(order);
            return table.getItem(order);
        } catch (DynamoDbException e) {
            String message = "Failed to save order";
            LOGGER.error(message, e);
            throw new RuntimeException(message);
        }
    }

    private static void populateIds(Order order) {
        if (!StringUtils.hasLength(order.getId())) {
            order.setId(String.valueOf(UUID.randomUUID()));
            order.setOrderId("ORDER - " + UUID.randomUUID().toString());
        }
        if (order.getLineItems() != null) {
            order.getLineItems().forEach(lineItem -> {
                if (!StringUtils.hasLength(lineItem.getId())) {
                    lineItem.setId(String.valueOf(UUID.randomUUID()));
                }
            });
        }
        order.setCustomer(getHardcodedCustomer());
    }

    private static Customer getHardcodedCustomer() {
        return  new Customer("CUSTOMER01", "Edgar Pecero", "edgar_pecero@hotmail.com");
    }
}
