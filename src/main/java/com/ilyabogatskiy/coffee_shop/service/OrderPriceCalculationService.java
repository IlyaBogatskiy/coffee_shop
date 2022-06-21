package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import java.math.BigDecimal;

public interface OrderPriceCalculationService {

    BigDecimal orderPriceCalculation(Order order);
}
