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

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order by id " + id + " was not found"));
    }

    public Optional<OrderItem> addOrderItem(Long id, OrderItem orderItem) {
        var coffeeVariety = coffeeVarietyService.findCoffeeVarietyById(orderItem.getCoffeeVariety().getId());
        if (coffeeVariety == null) {
            return Optional.empty();
        }

        var order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order by id " + id + " was not found"));
        if (order == null) {
            return Optional.empty();
        }

        order.setOrderItems(orderItems);
        orderItemRepository.save(orderItem);
        orderPriceCalculationService.orderPriceCalculation(order);
        orderRepository.save(order);

        return Optional.of(orderItem);
    }

    public OrderItem addOrderItemWithoutOrder(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public Order addOrder(Order order) {
        order.setOrderPrice(BigDecimal.ZERO);

        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteOrderById(id);
    }
}
