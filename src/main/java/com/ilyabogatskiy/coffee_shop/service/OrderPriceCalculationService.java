package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import java.math.BigDecimal;

public interface OrderPriceCalculationService {

    /** Стоимость заказа с учетом скидки на доставку */
    BigDecimal orderPriceCalculation(Order order);

    /** Стоимость 1 позиции заказа с учетом скидки */
    BigDecimal orderItemPriceCalculation(OrderItem orderItem);
}
