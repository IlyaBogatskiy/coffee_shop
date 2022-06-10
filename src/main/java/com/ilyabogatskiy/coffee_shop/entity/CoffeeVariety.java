package com.ilyabogatskiy.coffee_shop.entity;

import lombok.Data;

import javax.persistence.*;

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

    @Column(name = "variety_availability")
    private Boolean available;
}
