package com.ilyabogatskiy.coffee_shop.controller;

import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.dto.mapper.CoffeeVarietyMapper;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Api(tags = "coffee-variety-rest-controller")
@RequestMapping("/api/v1/variety")
@RequiredArgsConstructor
public class CoffeeVarietyRestController {

    private final CoffeeVarietyService coffeeVarietyService;
    private final CoffeeVarietyMapper coffeeVarietyMapper;

    @GetMapping("/all")
    @ApiOperation(value = "Получение списка сортов кофе")
    public List<CoffeeVarietyDto> getAllCoffeeVarieties() {
        List<CoffeeVariety> listCoffeeVariety = coffeeVarietyService.findAll();
        return coffeeVarietyMapper.toDto(listCoffeeVariety);
    }

    @GetMapping("/available")
    @ApiOperation(value = "Получение списка доступных сортов кофе")
    public List<CoffeeVarietyDto> getAllAvailableCoffeeVarieties() {
        List<CoffeeVariety> listCoffeeVarietyIsAvailable = coffeeVarietyService.findAllAvailable();
        return coffeeVarietyMapper.toDto(listCoffeeVarietyIsAvailable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение сорта кофе по id")
    public CoffeeVarietyDto getCoffeeVarietyById(
            @ApiParam(value = "coffee variety id", required = true) @RequestParam Long id
    ) {
        CoffeeVariety coffeeVariety = coffeeVarietyService.findById(id);
        return coffeeVarietyMapper.toDto(coffeeVariety);
    }

    @PostMapping()
    @ApiOperation(value = "Добавление сорта кофе")
    public CoffeeVarietyDto addCoffeeVariety(
            @ApiParam(value = "coffee variety", required = true) @RequestBody CoffeeVarietyDto coffeeVarietyDto
    ) {
        CoffeeVariety coffeeVariety = coffeeVarietyService.add(coffeeVarietyMapper.toModel(coffeeVarietyDto));
        return coffeeVarietyMapper.toDto(coffeeVariety);
    }

    @PutMapping()
    @ApiOperation(value = "Обновление сорта кофе")
    public CoffeeVarietyDto updateCoffeeVarieties(
            @ApiParam(value = "coffee variety", required = true) @RequestBody CoffeeVarietyDto coffeeVarietyDto) {
        CoffeeVariety coffeeVariety = coffeeVarietyService.edit(coffeeVarietyMapper.toModel(coffeeVarietyDto));
        return coffeeVarietyMapper.toDto(coffeeVariety);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление сорта кофе по id")
    public void deleteCoffeeVarietyById(
            @ApiParam(value = "coffee variety id", required = true) @RequestParam Long id
    ) {
        coffeeVarietyService.delete(id);
    }
}
