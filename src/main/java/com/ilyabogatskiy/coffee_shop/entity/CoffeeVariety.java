package com.ilyabogatskiy.coffee_shop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "coffee_variety")
public class CoffeeVariety {
    //Сорт кофе: название, цена за чашку, available флаг
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_of_variety")
    private String name;

    @Column(name = "price")
    private Integer price;

    private Boolean available;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grade")
    private List<OrderItem> orderItems;
}
