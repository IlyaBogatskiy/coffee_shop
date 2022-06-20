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

    @OneToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private CoffeeVariety coffeeVariety;

    @Column(name = "amount")
    private Integer cups;
}
