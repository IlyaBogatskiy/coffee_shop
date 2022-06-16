package com.ilyabogatskiy.coffee_shop.service.interfaces;

import com.ilyabogatskiy.coffee_shop.models.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order saveOrder(BigDecimal price, Order order);
}
