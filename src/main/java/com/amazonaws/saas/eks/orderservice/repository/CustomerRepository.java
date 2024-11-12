package com.amazonaws.saas.eks.orderservice.repository;

import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.time.Instant;
import java.util.Date;

@Repository
public class CustomerRepository {
    private static final Logger LOGGER = LogManager.getLogger(CustomerRepository.class);
    private final DynamoDbTable<Customer> table;

    public CustomerRepository(DynamoDbEnhancedClient client) {
        this.table = client.table(Customer.TABLE, TableSchema.fromBean(Customer.class));
    }

    public Customer save(Customer customer) {
        if (customer.getCreated() == null) {
            customer.setCreated(Instant.now());
        }
        if (customer.getModified() == null) {
            customer.setModified(Instant.now());
        }
        try {
            table.putItem(customer);
            return table.getItem(customer);
        } catch (DynamoDbException e) {
            String message = "Failed to save customer";
            LOGGER.error(message, e);
            throw new RuntimeException(message);
        }
    }

    public Customer findById(String id) {
        try {
            return table.getItem(getKey(id));
        } catch (Exception e) {
            String message = String.format("Get Customer by ID failed %s",  e.getMessage());
            LOGGER.error(message);
            throw new RuntimeException(message);
        }
    }

    public Customer update(Customer customer) {
        try {
            Customer model = findById(customer.getId());
            if (customer.getName() != null) {
                model.setName(customer.getName());
            }
            model.setModified(new Date().toInstant());
            return table.updateItem(model);
        } catch (Exception e) {
            String message = String.format("Update Customer by ID failed %s", e.getMessage());
            LOGGER.error(message);
            throw new RuntimeException(message);
        }
    }

    public void deleteById(String customerId) {
        try {
            table.deleteItem(getKey(customerId));
        } catch (Exception e) {
            String message = String.format("Delete Customer failed %s", e.getMessage());
            LOGGER.error(message);
            throw new RuntimeException(message);
        }
    }

    private static Key getKey(String customerId) {
        return Key.builder()
                .partitionValue(EntityType.CUSTOMER.getLabel())
                .sortValue(customerId)
                .build();
    }
}
