package com.amazonaws.saas.eks.orderservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.saas.eks.orderservice.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private OrderController orderController;

    @Test
    void contextLoads() throws Exception {
        assertThat(orderController).isNotNull();
    }
}
