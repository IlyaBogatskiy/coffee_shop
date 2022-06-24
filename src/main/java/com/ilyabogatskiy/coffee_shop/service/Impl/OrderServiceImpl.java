package com.ilyabogatskiy.coffee_shop.service.Impl;

import com.ilyabogatskiy.coffee_shop.exception.CoffeeVarietyNotFoundException;
import com.ilyabogatskiy.coffee_shop.exception.OrderNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.CoffeeVarietyRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import com.ilyabogatskiy.coffee_shop.service.OrderPriceCalculationService;
import com.ilyabogatskiy.coffee_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderPriceCalculationService orderPriceCalculationService;
    private final CoffeeVarietyRepository coffeeVarietyRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        Order maybeOrder = orderRepository.findById(id).orElse(null);
        if (maybeOrder == null) {
            log.warn("Заказа с указанным идентификатором ({}) нет в базе данных", id);
            throw new OrderNotFoundException("Заказа с указанным идентификатором нет в базе данных");
        }
        return maybeOrder;
    }

    @Override
    public void delete(Long id) {
        Order maybeOrder = orderRepository.findById(id).orElse(null);
        if (maybeOrder == null) {
            log.warn("Заказа с указанным идентификатором ({}) нет в базе данных", id);
            throw new OrderNotFoundException("Заказа с указанным идентификатором нет в базе данных");
        }
        log.info("Заказ ({}) удалён", findById(id).getId());
        orderRepository.deleteById(id);
    }

    @Override
    public Order add(Order order) {
        BigDecimal totalPrice = orderPriceCalculationService.orderPriceCalculation(order);
        order.setOrderPrice(totalPrice);
        log.info("Заказ ({}) на сумму ({}) добавлен", order.getId(), totalPrice);
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public OrderItem addItem(OrderItem orderItem) {
        CoffeeVariety coffeeVariety = coffeeVarietyRepository.findById(orderItem.getCoffeeVariety().getId()).orElse(null);
        if (coffeeVariety == null) {
            log.warn("Cорта кофе с указанным идентификатором ({}) нет в базе данных", orderItem.getCoffeeVariety().getId());
            throw new CoffeeVarietyNotFoundException("Сорта кофе с указанным идентификатором нет в базе данных");
        }
        orderItem.setCoffeeVariety(coffeeVariety);
        orderItem.setCups(orderItem.getCups());
        orderItem.setOrder(orderItem.getOrder());
        log.info("Позиция заказа ({}) добавлена", orderItem.getId());
        return orderItemRepository.saveAndFlush(orderItem);
    }

    @Override
    public Order edit(Order order) {
        Order maybeOrder = orderRepository.findById(order.getId()).orElse(null);
        if (maybeOrder == null) {
            log.warn("Заказа с указанным идентификатором ({}) нет в базе данных", order.getId());
            throw new OrderNotFoundException("Заказа с указанным идентификатором нет в базе данных");
        }
        BigDecimal totalPrice = orderPriceCalculationService.orderPriceCalculation(order);
        order.setOrderPrice(totalPrice);
        log.info("Заказ ({}) изменен, сумма перерасчитана ({})", order.getId(), totalPrice);
        return orderRepository.saveAndFlush(order);
    }
}
