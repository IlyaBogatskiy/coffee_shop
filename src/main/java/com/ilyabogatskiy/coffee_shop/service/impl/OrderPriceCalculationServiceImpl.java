package com.ilyabogatskiy.coffee_shop.service.impl;

import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import com.ilyabogatskiy.coffee_shop.service.interfaces.OrderPriceCalculationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderPriceCalculationServiceImpl implements OrderPriceCalculationService {

    private final OrderRepository orderRepository;

    @Value("${cafe.freeCup.n}")
    private Integer freeCup;
    @Value("${cafe.freeDelivery.x}")
    private Integer freeDeliveryPrice;
    @Value("${cafe.defaultDelivery.m}")
    private Integer defaultDeliveryPrice;

    public OrderPriceCalculationServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Integer orderPriceCalculation(Long id) {
//        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItem> orderItems = orderRepository.findById(id)
                .get()
                .getOrderItems();
        Integer fullPrice = 0;

        for (OrderItem orderItem : orderItems) {
            fullPrice += (orderItem.getCups()
                    * orderItem.getCoffeeVariety().getPrice()
                    - orderItem.getCups()
                    * orderItem.getCoffeeVariety().getPrice() / freeCup);
        }

        if (fullPrice < freeDeliveryPrice) {
            fullPrice += defaultDeliveryPrice;
        }

        return fullPrice;
    }
}
