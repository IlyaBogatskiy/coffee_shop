package com.ilyabogatskiy.coffee_shop.models;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime orderDate;

    @Column(name = "customer")
    private String customerName;

    @Column(name = "address")
    private String deliveryAddress;

    @Column(name = "total_price")
    private BigDecimal orderPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
