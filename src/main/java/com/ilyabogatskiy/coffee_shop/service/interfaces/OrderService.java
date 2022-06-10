package com.ilyabogatskiy.coffee_shop.service.interfaces;

import com.ilyabogatskiy.coffee_shop.entity.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.entity.Order;
import com.ilyabogatskiy.coffee_shop.entity.OrderItem;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Integer orderPriceCalculation(List<CoffeeVariety> coffeeVarieties,
                                  List<OrderItem> orderItems);
}
