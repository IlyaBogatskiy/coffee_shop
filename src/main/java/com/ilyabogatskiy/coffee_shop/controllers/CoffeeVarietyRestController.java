package com.ilyabogatskiy.coffee_shop.controllers;

import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.mapper.CoffeeVarietyMapper;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.service.Impl.CoffeeVarietyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Coffee Variety Rest Controller")
@RequestMapping("/variety")
@RequiredArgsConstructor
public class CoffeeVarietyRestController {

    private final CoffeeVarietyServiceImpl coffeeVarietyService;
    private final CoffeeVarietyMapper coffeeVarietyMapper;

    @ApiOperation(value = "getAllCoffeeVarieties", notes = "Получение списка сортов кофе")
    @GetMapping("/all")
    public List<CoffeeVariety> getAllCoffeeVarieties() {
        return coffeeVarietyService.findAll();
    }

    @ApiOperation(value = "getAllAvailableCoffeeVarieties", notes = "Получение списка сортов кофе с флагом available")
    @GetMapping("/all_available")
    public List<CoffeeVariety> getAllAvailableCoffeeVarieties() {
        return coffeeVarietyService.findAllAvailable();
    }

    @ApiOperation(value = "getCoffeeVarietyById", notes = "Получение сорта кофе по id")
    @GetMapping("/find/{id}")
    public CoffeeVariety getCoffeeVarietyById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит поиск сорта кофе",
            example = "1",
            required = true
    ) @PathVariable Long id, @RequestBody CoffeeVarietyDto coffeeVarietyDto) {
        CoffeeVariety coffeeVariety = coffeeVarietyMapper.toModel(coffeeVarietyDto);
        coffeeVarietyService.findById(id);
        return coffeeVariety;
    }

    @ApiOperation(value = "addCoffeeVariety", notes = "Добавление сорта кофе")
    @PostMapping("/add")
    public CoffeeVariety addCoffeeVariety(@RequestBody CoffeeVarietyDto coffeeVarietyDto) {
        CoffeeVariety coffeeVariety = coffeeVarietyMapper.toModel(coffeeVarietyDto);
        coffeeVarietyService.add(coffeeVariety);
        return coffeeVariety;
    }

    @ApiOperation(value = "updateCoffeeVarieties", notes = "Обновление сорта кофе")
    @PutMapping("/update")
    public CoffeeVariety updateCoffeeVarieties(@RequestBody CoffeeVarietyDto coffeeVarietyDto) {
        CoffeeVariety coffeeVariety = coffeeVarietyMapper.toModel(coffeeVarietyDto);
        coffeeVarietyService.edit(coffeeVariety);
        return coffeeVariety;
    }

    @ApiOperation(value = "deleteCoffeeVarietyById", notes = "Удаление сорта кофе по id")
    @DeleteMapping("/delete/{id}")
    public CoffeeVariety deleteCoffeeVarietyById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит удаление сорта кофе",
            example = "1",
            required = true
    ) @PathVariable Long id, @RequestBody CoffeeVarietyDto coffeeVarietyDto) {
        CoffeeVariety coffeeVariety = coffeeVarietyMapper.toModel(coffeeVarietyDto);
        coffeeVarietyService.delete(id);
        return coffeeVariety;
    }
}
