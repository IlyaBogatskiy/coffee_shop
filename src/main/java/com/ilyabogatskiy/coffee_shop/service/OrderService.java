package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.exception.CoffeeVarietyNotFoundException;
import com.ilyabogatskiy.coffee_shop.exception.OrderNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderPriceCalculationService orderPriceCalculationService;
    private final CoffeeVarietyService coffeeVarietyService;
    private final OrderRepository orderRepository;
    private final List<OrderItem> orderItems;

    public OrderService(OrderItemRepository orderItemRepository, OrderPriceCalculationService orderPriceCalculationService, CoffeeVarietyService coffeeVarietyService, OrderRepository orderRepository, List<OrderItem> orderItems) {
        this.orderItemRepository = orderItemRepository;
        this.orderPriceCalculationService = orderPriceCalculationService;
        this.coffeeVarietyService = coffeeVarietyService;
        this.orderRepository = orderRepository;
        this.orderItems = orderItems;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order by id " + id + " was not found"));
    }

    public Order createOrder(Order order) {
        order.setCustomerName(order.getCustomerName());
        order.setDeliveryAddress(order.getDeliveryAddress());
        order.setOrderItems(orderItems);

        orderPriceCalculationService.orderPriceCalculation(order);

        return orderRepository.save(order);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        var coffeeVariety = coffeeVarietyService.findCoffeeVarietyById(orderItem.getCoffeeVariety().getId());
        if (coffeeVariety == null) {
            return null;
        }

        orderItem.setCoffeeVariety(coffeeVariety);
        orderItem.setCups(orderItem.getCups());

        orderItemRepository.save(orderItem);

        return orderItem;
    }

    public Order updateOrderById(Long id) {
        var order = new Order();

        orderRepository.findOrderById(id);

        order.setCustomerName(order.getCustomerName());
        order.setDeliveryAddress(order.getDeliveryAddress());
        order.setOrderItems(orderItems);

        orderPriceCalculationService.orderPriceCalculation(order);

        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteOrderById(id);
    }
}
