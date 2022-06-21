package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Long id);

    void delete(Long id);

    Order add(Order order);

    OrderItem addItem(OrderItem orderItem);

    Order edit(Order order);
}
