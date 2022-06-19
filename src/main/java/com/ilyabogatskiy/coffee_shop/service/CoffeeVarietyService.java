package com.ilyabogatskiy.coffee_shop.service;

import com.ilyabogatskiy.coffee_shop.exception.CoffeeVarietyNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.repository.CoffeeVarietyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CoffeeVarietyService {

    private final CoffeeVarietyRepository coffeeVarietyRepository;

    public CoffeeVarietyService(CoffeeVarietyRepository coffeeVarietyRepository) {
        this.coffeeVarietyRepository = coffeeVarietyRepository;
    }

    public List<CoffeeVariety> findCoffeeVarietiesByAvailableIsTrue() {
        return coffeeVarietyRepository.findCoffeeVarietiesByAvailableIsTrue();
    }

    public CoffeeVariety findCoffeeVarietyById(Long id) {
        return coffeeVarietyRepository.findCoffeeVarietyById(id)
                .orElseThrow(() -> new CoffeeVarietyNotFoundException("Coffee variety by id " + id + " was not found"));
    }

    public CoffeeVariety addCoffeeVariety(CoffeeVariety coffeeVariety) {
        return coffeeVarietyRepository.save(coffeeVariety);
    }

    public CoffeeVariety updateCoffeeVariety(CoffeeVariety coffeeVariety) {
        return coffeeVarietyRepository.save(coffeeVariety);
    }

    public void deleteCoffeeVariety(Long id) {
        coffeeVarietyRepository.deleteCoffeeVarietyById(id);
    }
}
