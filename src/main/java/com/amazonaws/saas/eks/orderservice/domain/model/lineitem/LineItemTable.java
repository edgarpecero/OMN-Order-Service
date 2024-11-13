package com.amazonaws.saas.eks.orderservice.domain.model.lineitem;

import com.amazonaws.saas.eks.orderservice.domain.model.order.OrderTable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LINE_ITEMS")
@Data
public class LineItemTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderTable order;  // Back reference to Order

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;
}
