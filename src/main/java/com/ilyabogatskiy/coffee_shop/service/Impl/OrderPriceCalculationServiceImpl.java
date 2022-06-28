package com.ilyabogatskiy.coffee_shop.service.Impl;

import com.ilyabogatskiy.coffee_shop.exception.OrderItemNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.OrderPriceCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPriceCalculationServiceImpl implements OrderPriceCalculationService {

    @Value("${cafe.freeCup.n}")
    private Integer freeCup;

    @Value("${cafe.freeDelivery.x}")
    private BigDecimal freeDeliveryPrice;

    @Value("${cafe.defaultDelivery.m}")
    private BigDecimal defaultDeliveryPrice;

    //Общая стоимость заказа
    @Override
    public Order orderPriceCalculation(Order order) {
        order.setOrderPrice(
                order.getOrderItems()
                        .stream()
                        .map(this::orderItemPriceCalculation)
                        .map(OrderItem::getOrderItemPrice)
                        .reduce(BigDecimal::add)
                        .orElseThrow(() -> new OrderItemNotFoundException("Позиции заказа " + order.getOrderItems() + " не найдены"))
        );
        var deliveryPrice = deliveryPriceWithDiscountCalculation(order);
        order.setOrderPrice(order.getOrderPrice().add(deliveryPrice));
        return order;
    }

    //Стоимость ПОЗИЦИИ ЗАКАЗА
    @Override
    public OrderItem orderItemPriceCalculation(OrderItem orderItem) {
        var cupCount = orderItem.getCups();
        var orderPrice = orderItem.getCoffeeVariety().getPrice();
        var itemPrice = orderPrice.multiply(BigDecimal.valueOf(cupCount - cupCount / freeCup));
        orderItem.setOrderItemPrice(itemPrice);
        return orderItem;
    }

    //Стоимость доставки с учетом скидки
    private BigDecimal deliveryPriceWithDiscountCalculation(Order order) {
        var orderPrice = order.getOrderPrice();
        if (orderPrice.compareTo(freeDeliveryPrice) < 0 && order.getOrderItems().size() > 0) {
            log.info("Стоимость доставки ({})", defaultDeliveryPrice);
            return defaultDeliveryPrice;
        }
        return BigDecimal.ZERO;
    }
}
