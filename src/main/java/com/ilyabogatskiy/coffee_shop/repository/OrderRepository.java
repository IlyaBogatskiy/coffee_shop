package com.ilyabogatskiy.coffee_shop.repository;

import com.ilyabogatskiy.coffee_shop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
