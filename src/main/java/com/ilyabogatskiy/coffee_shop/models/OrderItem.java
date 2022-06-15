package com.ilyabogatskiy.coffee_shop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    //Сущность позиции заказа: сорт кофе, число чашек
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "grade")
    @OneToOne(fetch = FetchType.LAZY)
    private CoffeeVariety coffeeVariety;

    @Column(name = "amount")
    private Integer cups;
}