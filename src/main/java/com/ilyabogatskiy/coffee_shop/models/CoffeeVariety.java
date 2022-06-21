package com.ilyabogatskiy.coffee_shop.models;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "coffee_variety")
public class CoffeeVariety {

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
