package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Long id);

    Order add(Order order);

    void delete(Long id);
}
