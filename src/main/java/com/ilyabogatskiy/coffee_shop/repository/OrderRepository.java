package com.ilyabogatskiy.coffee_shop.repository;

import com.ilyabogatskiy.coffee_shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
