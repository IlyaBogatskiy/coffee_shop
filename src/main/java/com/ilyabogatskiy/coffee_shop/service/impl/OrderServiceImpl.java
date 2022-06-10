package com.ilyabogatskiy.coffee_shop.service.impl;

import com.ilyabogatskiy.coffee_shop.entity.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.entity.Order;
import com.ilyabogatskiy.coffee_shop.entity.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import com.ilyabogatskiy.coffee_shop.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Value("${cafe.freeCup.n}")
    private Integer freeCup;
    @Value("${cafe.freeDelivery.x}")
    private Integer freeDeliveryPrice;
    @Value("${cafe.defaultDelivery.m}")
    private Integer defaultDeliveryPrice;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Integer orderPriceCalculation(List<CoffeeVariety> coffeeVarieties,
                                         List<OrderItem> orderItems) {
        HashMap<Long, Integer> coffeeVarietiesPrice = new HashMap<>();
        Integer fullPrice = 0;

        for (CoffeeVariety coffeeVariety : coffeeVarieties)  {
            coffeeVarietiesPrice.put(coffeeVariety.getId(), coffeeVariety.getPrice());
        }

        for (OrderItem orderItem : orderItems) {
            fullPrice += (orderItem.getCups() - orderItem.getCups() / freeCup)
                    * coffeeVarietiesPrice.get(orderItem.getGradeId());
        }
        if (fullPrice < freeDeliveryPrice) {
           fullPrice += defaultDeliveryPrice;
        }

        return fullPrice;
    }
}
