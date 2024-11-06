package com.amazonaws.saas.eks.orderservice.repository;

import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Counter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;

@Repository
public class CounterRepository {
    private static final Logger LOGGER = LogManager.getLogger(CounterRepository.class);

    private final DynamoDbTable<Counter> table;

    public CounterRepository(DynamoDbEnhancedClient client) {
        this.table = client.table(Counter.TABLE, TableSchema.fromBean(Counter.class));
    }

    public Counter get(String counterType) {
        try {
            Counter counter = Counter.builder().entity(EntityType.COUNTER.getLabel()).id(counterType).build();
            return table.updateItemWithResponse(UpdateItemEnhancedRequest.builder(Counter.class).item(counter).build()).attributes();
        } catch (Exception e) {
            LOGGER.error("Failed to get counter", e);
            throw new RuntimeException("Failed to get counter", e);
        }
    }
}
