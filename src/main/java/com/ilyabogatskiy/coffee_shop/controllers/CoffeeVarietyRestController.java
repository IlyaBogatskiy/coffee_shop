package com.ilyabogatskiy.coffee_shop.controllers;

import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Coffee Variety Rest Controller")
@RequestMapping("/variety")
@RequiredArgsConstructor
public class CoffeeVarietyRestController {

    private final CoffeeVarietyService coffeeVarietyService;

    @ApiOperation(value = "getAllCoffeeVarieties", notes = "Получение списка сортов кофе")
    @GetMapping("/all")
    public ResponseEntity<List<CoffeeVariety>> getAllCoffeeVarieties() {
        return new ResponseEntity<>(coffeeVarietyService.findCoffeeVarietiesByAvailableIsTrue(),
                HttpStatus.OK);
    }

    @ApiOperation(value = "getCoffeeVarietyById", notes = "Получение сорта кофе по id")
    @GetMapping("/find/{id}")
    public ResponseEntity<CoffeeVariety> getCoffeeVarietyById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит поиск сорта кофе",
            example = "1",
            required = true)
                                                              @PathVariable Long id) {
        return new ResponseEntity<>(coffeeVarietyService.findCoffeeVarietyById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "addCoffeeVariety", notes = "Добавление сорта кофе")
    @PostMapping("/add")
    public ResponseEntity<CoffeeVariety> addCoffeeVariety(@RequestBody CoffeeVariety coffeeVariety) {
        return new ResponseEntity<>(coffeeVarietyService.addCoffeeVariety(coffeeVariety),
                HttpStatus.CREATED);
    }

    @ApiOperation(value = "updateCoffeeVarieties", notes = "Обновление сорта кофе")
    @PutMapping("/update")
    public ResponseEntity<CoffeeVariety> updateCoffeeVarieties(@RequestBody CoffeeVariety coffeeVariety) {
        return new ResponseEntity<>(coffeeVarietyService.updateCoffeeVariety(coffeeVariety),
                HttpStatus.OK);
    }

    @ApiOperation(value = "deleteCoffeeVarietyById", notes = "Удаление сорта кофе по id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoffeeVarietyById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит удаление сорта кофе",
            example = "1",
            required = true)
                                                     @PathVariable Long id) {
        coffeeVarietyService.deleteCoffeeVariety(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
