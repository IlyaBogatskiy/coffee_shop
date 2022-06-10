package com.ilyabogatskiy.coffee_shop.repository;

import com.ilyabogatskiy.coffee_shop.entity.CoffeeVariety;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeVarietyRepository extends JpaRepository<CoffeeVariety, Long> {
}
