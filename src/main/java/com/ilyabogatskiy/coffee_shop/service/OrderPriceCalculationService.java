package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderPriceCalculationService {

    private final List<OrderItem> orderItems;

    @Value("${cafe.freeCup.n}")
    private Integer freeCup;
    @Value("${cafe.freeDelivery.x}")
    private BigDecimal freeDeliveryPrice;
    @Value("${cafe.defaultDelivery.m}")
    private BigDecimal defaultDeliveryPrice;

    //Общая стоимость заказа
    public Order orderPriceCalculation(Order order) {
        var deliveryPrice = deliveryPriceWithDiscountCalculation(order);
        var orderItemPrice =  orderItemPriceCalculation(orderItems);

        order.setOrderPrice(deliveryPrice.add(orderItemPrice));

        return order;
    }

    //Стоимость одной позиции заказа
    private BigDecimal orderItemPriceCalculation(List<OrderItem> orderItems) {
        var fullPrice = new BigDecimal(0);

        for (OrderItem orderItem : orderItems) {
            var cupCount = orderItem.getCups();
            var orderPrice = orderItem.getCoffeeVariety().getPrice();
            fullPrice = orderPrice.multiply(BigDecimal.valueOf(cupCount - cupCount / freeCup));
        }

        return fullPrice;
    }

    //Стоимость доставки с учетом скидки
    private BigDecimal deliveryPriceWithDiscountCalculation(Order order) {
        if (order.getOrderPrice().compareTo(freeDeliveryPrice) < 0 && order.getOrderItems().size() > 0) {
            return defaultDeliveryPrice;
        }

        return BigDecimal.ZERO;
    }
}
