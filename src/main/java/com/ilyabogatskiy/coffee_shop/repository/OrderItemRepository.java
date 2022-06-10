package com.ilyabogatskiy.coffee_shop.repository;

import com.ilyabogatskiy.coffee_shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
