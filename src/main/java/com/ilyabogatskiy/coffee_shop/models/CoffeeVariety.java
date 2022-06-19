package com.ilyabogatskiy.coffee_shop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private BigDecimal price;

    @Column(name = "variety_availability")
    private Boolean available;
}
