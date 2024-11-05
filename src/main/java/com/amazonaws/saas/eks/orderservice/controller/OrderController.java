package com.amazonaws.saas.eks.orderservice.controller;

import com.amazonaws.saas.eks.orderservice.config.AwsSecretsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private AwsSecretsConfig awsSecretsConfig;
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println(awsSecretsConfig.getAccessKey());
        return "Hello, World! this is Edgar - 1";
    }

    @GetMapping(value = "/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Map<String, Object>> getOrders() {
        return Arrays.asList(
                Map.of(
                        "orderId", "ORD001",
                        "customer", Map.of(
                                "customerId", "CUST001",
                                "name", "John Doe",
                                "email", "johndoe@example.com",
                                "phone", "123-456-7890"
                        ),
                        "items", List.of(
                                Map.of("productId", "PROD001", "name", "Laptop", "quantity", 1, "price", 1200.00),
                                Map.of("productId", "PROD002", "name", "Mouse", "quantity", 1, "price", 25.00)
                        ),
                        "orderDate", "2024-11-04",
                        "status", "Processing",
                        "payment", Map.of(
                                "method", "Credit Card",
                                "status", "Paid"
                        ),
                        "shipping", Map.of(
                                "address", "123 Main St, Cityville",
                                "status", "Shipped",
                                "trackingNumber", "TRACK123"
                        ),
                        "totalAmount", 1225.00
                ),
                Map.of(
                        "orderId", "ORD002",
                        "customer", Map.of(
                                "customerId", "CUST002",
                                "name", "Jane Smith",
                                "email", "janesmith@example.com",
                                "phone", "987-654-3210"
                        ),
                        "items", List.of(
                                Map.of("productId", "PROD003", "name", "Phone", "quantity", 1, "price", 800.00),
                                Map.of("productId", "PROD004", "name", "Charger", "quantity", 1, "price", 20.00)
                        ),
                        "orderDate", "2024-11-03",
                        "status", "Completed",
                        "payment", Map.of(
                                "method", "PayPal",
                                "status", "Paid"
                        ),
                        "shipping", Map.of(
                                "address", "456 Oak St, Townville",
                                "status", "Delivered",
                                "trackingNumber", "TRACK456"
                        ),
                        "totalAmount", 820.00
                ),
                Map.of(
                        "orderId", "ORD003",
                        "customer", Map.of(
                                "customerId", "CUST003",
                                "name", "Alice Johnson",
                                "email", "alicejohnson@example.com",
                                "phone", "555-123-4567"
                        ),
                        "items", List.of(
                                Map.of("productId", "PROD005", "name", "Tablet", "quantity", 1, "price", 600.00)
                        ),
                        "orderDate", "2024-11-02",
                        "status", "Pending",
                        "payment", Map.of(
                                "method", "Bank Transfer",
                                "status", "Pending"
                        ),
                        "shipping", Map.of(
                                "address", "789 Pine St, Hamletville",
                                "status", "Not Shipped",
                                "trackingNumber", "TRACK789"
                        ),
                        "totalAmount", 600.00
                ),
                Map.of(
                        "orderId", "ORD004",
                        "customer", Map.of(
                                "customerId", "CUST004",
                                "name", "Bob Brown",
                                "email", "bobbrown@example.com",
                                "phone", "321-654-0987"
                        ),
                        "items", List.of(
                                Map.of("productId", "PROD006", "name", "Headphones", "quantity", 2, "price", 150.00)
                        ),
                        "orderDate", "2024-11-01",
                        "status", "Cancelled",
                        "payment", Map.of(
                                "method", "Credit Card",
                                "status", "Refunded"
                        ),
                        "shipping", Map.of(
                                "address", "101 Maple St, Villagetown",
                                "status", "Cancelled",
                                "trackingNumber", "TRACK101"
                        ),
                        "totalAmount", 300.00
                )
        );
    }
}
