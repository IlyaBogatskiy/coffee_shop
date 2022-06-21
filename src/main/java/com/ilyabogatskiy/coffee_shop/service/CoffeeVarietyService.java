package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;

import java.util.List;

public interface CoffeeVarietyService {

    List<CoffeeVariety> findAll();

    List<CoffeeVariety> findAllAvailable();

    CoffeeVariety findById(Long id);

    void delete(Long id);

    CoffeeVariety add(CoffeeVariety coffeeVariety);

    CoffeeVariety edit(CoffeeVariety coffeeVariety);
}
