package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.exception.OrderNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderPriceCalculationService orderPriceCalculationService;
    private final OrderRepository orderRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderPriceCalculationService orderPriceCalculationService, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderPriceCalculationService = orderPriceCalculationService;
        this.orderRepository = orderRepository;
    }

    public Optional<OrderItem> newOrderItem(Long orderId, OrderItem orderItem) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order by id " + orderId + " was not found"));

        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);
        orderPriceCalculationService.orderPriceCalculation(order);
        orderRepository.save(order);

        return Optional.of(orderItem);
    }
}
