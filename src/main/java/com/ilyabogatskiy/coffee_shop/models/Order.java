package com.ilyabogatskiy.coffee_shop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    //Заказ: дата и время заказа, имя заказчика, адрес доставки, стоимость
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime orderDateTime;

    @Column(name = "customer")
    private String customerName;

    @Column(name = "address")
    private String deliveryAddress;

    @Column(name = "total_price")
    private BigDecimal orderPrice;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
