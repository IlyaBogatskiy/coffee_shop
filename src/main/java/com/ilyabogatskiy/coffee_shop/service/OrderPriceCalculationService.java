package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class OrderPriceCalculationService {

    @Value("${cafe.freeCup.n}")
    private Integer freeCup;
    @Value("${cafe.freeDelivery.x}")
    private BigDecimal freeDeliveryPrice;
    @Value("${cafe.defaultDelivery.m}")
    private BigDecimal defaultDeliveryPrice;

    //Подсчет общей суммы заказа
    public Order orderPriceCalculation(Order order) {
        order.setOrderPrice(null);
        return order;
    }

    //Совместная стоимость заказа и доставки со скидкой
    private Order orderPriceWithDeliveryPrice(Order order) {
        var deliveryPrice = deliveryPriceWithDiscountCalculation(order);

        order.setOrderPrice(order.getOrderPrice().add(deliveryPrice));

        return order;
    }

    //Стоимость одной позиции заказа
    private OrderItem orderItemPriceCalculation(OrderItem orderItem) {
        var cupCount = orderItem.getCups();
        var orderPrice = orderItem.getCoffeeVariety().getPrice();
        var fullPrice = orderPrice.multiply(BigDecimal.valueOf(cupCount - cupCount / freeCup));

        orderItem.getOrder().setOrderPrice(fullPrice);

        return orderItem;
    }

    //Стоимость доставки с учетом скидки
    private BigDecimal deliveryPriceWithDiscountCalculation(Order order) {
        if (order.getOrderPrice().compareTo(freeDeliveryPrice) < 0 && order.getOrderItems().size() > 0) {
            return defaultDeliveryPrice;
        }

        return BigDecimal.ZERO;
    }
}
