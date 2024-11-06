package com.amazonaws.saas.eks.orderservice.domain.model.order;

import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAtomicCounter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Counter {
    public static final String TABLE = "ORDERS-OMN";
    private String entity = EntityType.COUNTER.getLabel();
    private String id;
    private Long count;

    @DynamoDbPartitionKey
    public String getEntity() {
        return entity;
    }

    @DynamoDbSortKey
    public String getId() {
        return id;
    }

    @DynamoDbAtomicCounter
    public Long getCount() {
        return count;
    }
}
