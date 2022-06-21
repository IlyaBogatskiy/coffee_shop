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

    //Общая стоимость заказа
    @Override
    public BigDecimal orderPriceCalculation(Order order) {
        var orderPrice =  orderItemPriceCalculation(order);
        return deliveryPriceWithDiscountCalculation(orderPrice);
    }

    //Стоимость ВСЕХ ПОЗИЦИЙ ЗАКАЗА
    private BigDecimal orderItemPriceCalculation(Order order) {
        var fullPrice = new BigDecimal(0);

        for (OrderItem orderItem : order.getOrderItems()) {
            var cupCount = orderItem.getCups();
            var orderPrice = orderItem.getCoffeeVariety().getPrice();
            fullPrice = orderPrice.multiply(BigDecimal.valueOf(cupCount - cupCount / freeCup));
        }

        return fullPrice;
    }

    //Стоимость доставки с учетом скидки на доставку
    private BigDecimal deliveryPriceWithDiscountCalculation(BigDecimal orderPrice) {
        if (orderPrice.compareTo(new BigDecimal(freeDeliveryPrice)) > 0) {
            log.info("Стоимость доставки ({})", orderPrice);
            return orderPrice;
        }
        var totalPrice = orderPrice.add(new BigDecimal(defaultDeliveryPrice));
        log.info("Стоимость доставки ({})", totalPrice);
        return totalPrice;
    }
}
