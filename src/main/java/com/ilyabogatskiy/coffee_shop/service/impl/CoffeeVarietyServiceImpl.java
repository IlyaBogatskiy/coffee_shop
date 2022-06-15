package com.ilyabogatskiy.coffee_shop.service.impl;

import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.repository.CoffeeVarietyRepository;
import com.ilyabogatskiy.coffee_shop.service.interfaces.CoffeeVarietyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CoffeeVarietyServiceImpl implements CoffeeVarietyService {

    private final CoffeeVarietyRepository coffeeVarietyRepository;

    public CoffeeVarietyServiceImpl(CoffeeVarietyRepository coffeeVarietyRepository) {
        this.coffeeVarietyRepository = coffeeVarietyRepository;
    }

    @Override
    public List<CoffeeVariety> getAllCoffeeVarieties() {
        return coffeeVarietyRepository.getCoffeeVarietiesByAvailableIsTrue();
    }

    @Override
    public CoffeeVariety addCoffeeVariety(CoffeeVariety coffeeVariety) {
        return coffeeVarietyRepository.save(coffeeVariety);
    }

    @Override
    public void deleteCoffeeVariety(List<CoffeeVariety> coffeeVariety) {
        coffeeVarietyRepository.deleteAll();
    }
}
