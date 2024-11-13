package com.amazonaws.saas.eks.orderservice.domain.model.order;

import com.amazonaws.saas.eks.orderservice.domain.model.customer.CustomerTable;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.lineitem.LineItemTable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="ORDERS")
@Data
public class OrderTable {
    private static final String ENTITY = EntityType.ORDERS.getLabel();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment in SQL Server
    @Column(name = "id")
    private Long id;  // Change to Long for auto-generated ID

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineItemTable> lineItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerTable customer;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "modified", nullable = false)
    private LocalDateTime modified;

}
