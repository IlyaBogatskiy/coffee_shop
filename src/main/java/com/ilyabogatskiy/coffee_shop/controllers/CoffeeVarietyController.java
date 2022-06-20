package com.ilyabogatskiy.coffee_shop.controllers;

import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variety")
public class CoffeeVarietyController {

    private final CoffeeVarietyService coffeeVarietyService;

    public CoffeeVarietyController(CoffeeVarietyService coffeeVarietyService) {
        this.coffeeVarietyService = coffeeVarietyService;
    }

    @ApiOperation("Получение списка сортов кофе")
    @GetMapping("/all")
    public ResponseEntity<List<CoffeeVariety>> getAllCoffeeVarieties() {
        return new ResponseEntity<>(coffeeVarietyService.findCoffeeVarietiesByAvailableIsTrue(),
                HttpStatus.OK);
    }

    @ApiOperation("Получение сорта кофе по id")
    @GetMapping("/find/{id}")
    public ResponseEntity<CoffeeVariety> getCoffeeVarietyById(@PathVariable Long id) {
        return new ResponseEntity<>(coffeeVarietyService.findCoffeeVarietyById(id), HttpStatus.OK);
    }

    @ApiOperation("Добавление сорта кофе")
    @PostMapping("/add")
    public ResponseEntity<CoffeeVariety> addCoffeeVariety(@RequestBody CoffeeVariety coffeeVariety) {
        return new ResponseEntity<>(coffeeVarietyService.addCoffeeVariety(coffeeVariety),
                HttpStatus.CREATED);
    }

    @ApiOperation("Обновление сорта кофе")
    @PutMapping("/update")
    public ResponseEntity<CoffeeVariety> updateCoffeeVarieties(@RequestBody CoffeeVariety coffeeVariety) {
        return new ResponseEntity<>(coffeeVarietyService.updateCoffeeVariety(coffeeVariety),
                HttpStatus.OK);
    }

    @ApiOperation("Удаление сорта кофе по id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoffeeVarietyById(@PathVariable Long id) {
        coffeeVarietyService.deleteCoffeeVariety(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
