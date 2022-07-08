package com.ilyabogatskiy.coffee_shop.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "coffee_variety_id", nullable = false)
    private CoffeeVariety coffeeVariety;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer cups;

    @Column(name = "order_item_price")
    private BigDecimal orderItemPrice;
}
