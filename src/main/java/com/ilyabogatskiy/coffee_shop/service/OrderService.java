package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order newOrder(Order order) {
        order.setOrderPrice(BigDecimal.ZERO);

        return orderRepository.save(order);
    }
}
