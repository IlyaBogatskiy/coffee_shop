package com.ilyabogatskiy.coffee_shop.service.interfaces;

import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;

import java.util.List;

public interface CoffeeVarietyService {

    List<CoffeeVariety> getAllCoffeeVarieties();

    CoffeeVariety addCoffeeVariety(CoffeeVariety coffeeVariety);

    void deleteCoffeeVariety(List<CoffeeVariety> coffeeVariety);
}
