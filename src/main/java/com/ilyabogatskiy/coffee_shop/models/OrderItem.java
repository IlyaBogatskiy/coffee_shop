package com.ilyabogatskiy.coffee_shop.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "coffee_variety_id")
    private CoffeeVariety coffeeVariety;

    @Column(name = "amount")
    private Integer cups;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
