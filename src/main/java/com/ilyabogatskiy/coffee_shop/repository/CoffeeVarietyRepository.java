package com.ilyabogatskiy.coffee_shop.repository;

import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeVarietyRepository extends JpaRepository<CoffeeVariety, Long> {

    List<CoffeeVariety> findCoffeeVarietyByAvailableIsTrue();
}
