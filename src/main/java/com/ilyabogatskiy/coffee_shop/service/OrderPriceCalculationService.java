package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;

import java.math.BigDecimal;

public interface OrderPriceCalculationService {

    Order orderPriceCalculation(Order order);

    //Стоимость ПОЗИЦИИ ЗАКАЗА
    OrderItem orderItemPriceCalculation(OrderItem orderItem);
}
