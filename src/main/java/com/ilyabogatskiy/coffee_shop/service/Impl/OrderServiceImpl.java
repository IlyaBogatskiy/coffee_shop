package com.ilyabogatskiy.coffee_shop.service.Impl;

import com.ilyabogatskiy.coffee_shop.exception.OrderNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import com.ilyabogatskiy.coffee_shop.service.OrderPriceCalculationService;
import com.ilyabogatskiy.coffee_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final CoffeeVarietyService coffeeVarietyService;
    private final OrderPriceCalculationService orderPriceCalculationService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        var maybeOrder = orderRepository.findById(id).orElse(null);
        if (maybeOrder == null) {
            log.warn("Заказа с указанным идентификатором ({}) нет в базе данных", id);
            throw new OrderNotFoundException("Заказа с указанным идентификатором нет в базе данных");
        }
        return maybeOrder;
    }

    @Transactional
    @Override
    public Order add(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            addItem(orderItem);
        }
        BigDecimal orderTotalPrice = orderPriceCalculationService.orderPriceCalculation(order);
        order.setOrderPrice(orderTotalPrice);
        log.info("Заказ добавлен на сумму ({}) добавлен", order.getOrderPrice());
        return orderRepository.saveAndFlush(order);
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

    private OrderItem addItem(OrderItem orderItem) {
        CoffeeVariety coffeeVariety = coffeeVarietyService.findById(orderItem.getCoffeeVariety().getId());
        orderItem.setCoffeeVariety(coffeeVariety);
        BigDecimal itemTotalPrice = orderPriceCalculationService.orderItemPriceCalculation(orderItem);
        orderItem.setOrderItemPrice(itemTotalPrice);
        log.info("Позиция заказа ({}) ({})шт. на сумму ({}) добавлена", orderItem.getCoffeeVariety().getName(), orderItem.getCups(), itemTotalPrice);
        return orderItemRepository.saveAndFlush(orderItem);
    }
}
