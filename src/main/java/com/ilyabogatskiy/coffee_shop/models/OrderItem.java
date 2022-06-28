package com.ilyabogatskiy.coffee_shop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_variety_id", nullable = false)
    private CoffeeVariety coffeeVariety;

    @Column(name = "amount")
    private Integer cups;

    @Column(name = "order_item_price")
    private BigDecimal orderItemPrice;
}
