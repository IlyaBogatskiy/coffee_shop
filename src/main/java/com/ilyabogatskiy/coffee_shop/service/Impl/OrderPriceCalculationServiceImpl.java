package com.ilyabogatskiy.coffee_shop.service.Impl;

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
    private String freeDeliveryPrice;

    @Value("${cafe.defaultDelivery.m}")
    private String defaultDeliveryPrice;

    @Override
    public BigDecimal orderPriceCalculation(Order order) {
        BigDecimal orderPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : order.getOrderItems()) {
            orderPrice = orderPrice.add(orderItem.getOrderItemPrice());
        }
        return orderPrice.add(deliveryPrice(orderPrice));
    }

    @Override
    public BigDecimal orderItemPriceCalculation(OrderItem orderItem) {
        var cupCount = orderItem.getCups();
        var orderPrice = orderItem.getCoffeeVariety().getPrice();
        return orderPrice.multiply(BigDecimal.valueOf(cupCount - cupCount / freeCup));
    }

    /** Стоимость доставки */
    private BigDecimal deliveryPrice(BigDecimal orderPrice) {

        if (orderPrice.compareTo(new BigDecimal(freeDeliveryPrice)) < 0) {
            log.info("Стоимость доставки ({})", defaultDeliveryPrice);
            return new BigDecimal(defaultDeliveryPrice);
        }
        return BigDecimal.ZERO;
    }
}
