package com.ilyabogatskiy.coffee_shop.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    //Заказ: дата и время заказа, имя заказчика, адрес доставки, стоимость
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime localDateTime;

    @Column(name = "customer")
    private String customerName;

    @Column(name = "address")
    private String deliveryAddress;

    @Column(name = "total_price")
    private Integer orderPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItems;
}
