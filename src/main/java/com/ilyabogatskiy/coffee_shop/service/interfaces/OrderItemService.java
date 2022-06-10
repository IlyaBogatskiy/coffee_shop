package com.ilyabogatskiy.coffee_shop.service.interfaces;

import com.ilyabogatskiy.coffee_shop.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> getAllOrderItems();
}
