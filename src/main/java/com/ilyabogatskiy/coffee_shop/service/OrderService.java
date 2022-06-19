package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.exception.OrderNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order by id " + id + " was not found"));
    }

    public Order addOrder(Order order) {
        order.setOrderPrice(BigDecimal.ZERO);

        return orderRepository.save(order);
    }

    public Order updateCoffeeVariety(Order order) {
        return orderRepository.save(order);
    }

    public void deleteCoffeeVariety(Long id) {
        orderRepository.deleteOrderById(id);
    }
}
