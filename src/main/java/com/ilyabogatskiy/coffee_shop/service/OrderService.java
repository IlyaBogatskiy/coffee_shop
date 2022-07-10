package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Page<Order> orderPage(Pageable pageable);

    Order findById(Long id);

    Order add(Order order);

    void delete(Long id);
}
