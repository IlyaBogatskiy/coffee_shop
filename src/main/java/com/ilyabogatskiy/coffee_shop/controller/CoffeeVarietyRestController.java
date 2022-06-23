package com.ilyabogatskiy.coffee_shop.controller;

import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.mapper.CoffeeVarietyMapper;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Coffee Variety Rest Controller")
@RequestMapping("/variety")
@RequiredArgsConstructor
public class CoffeeVarietyRestController {

    private final CoffeeVarietyService coffeeVarietyService;
    private final CoffeeVarietyMapper coffeeVarietyMapper;

    @GetMapping("/all")
    @ApiOperation(value = "getAllCoffeeVarieties", notes = "Получение списка сортов кофе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список сортов кофе успешно получен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные получены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public List<CoffeeVariety> getAllCoffeeVarieties() {
        return coffeeVarietyService.findAll();
    }

    @GetMapping("/all_available")
    @ApiOperation(value = "getAllAvailableCoffeeVarieties", notes = "Получение списка сортов кофе с флагом available")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список сортов кофе с флагом available получен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные получены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public List<CoffeeVariety> getAllAvailableCoffeeVarieties() {
        return coffeeVarietyService.findAllAvailable();
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "getCoffeeVarietyById", notes = "Получение сорта кофе по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сорт кофе успешно получен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные получены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public CoffeeVariety getCoffeeVarietyById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит поиск сорта кофе",
            example = "1",
            required = true
    ) @PathVariable Long id) {
        return coffeeVarietyService.findById(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "addCoffeeVariety", notes = "Добавление сорта кофе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сорт кофе успешно добавлен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные добавлены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public CoffeeVarietyDto addCoffeeVariety(@RequestBody CoffeeVarietyDto coffeeVarietyDto) {
        CoffeeVariety coffeeVariety = coffeeVarietyService.add(coffeeVarietyMapper.toModel(coffeeVarietyDto));
        return coffeeVarietyMapper.toDto(coffeeVariety);
    }

    @PutMapping("/update")
    @ApiOperation(value = "updateCoffeeVarieties", notes = "Обновление сорта кофе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сорт кофе успешно обновлен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные обновлены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public CoffeeVarietyDto updateCoffeeVarieties(@RequestBody CoffeeVarietyDto coffeeVarietyDto) {
        CoffeeVariety coffeeVariety = coffeeVarietyService.edit(coffeeVarietyMapper.toModel(coffeeVarietyDto));
        return coffeeVarietyMapper.toDto(coffeeVariety);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "deleteCoffeeVarietyById", notes = "Удаление сорта кофе по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сорт кофе успешно удален"),
            @ApiResponse(code = 201, message = "Запрос принят и данные удалены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public void deleteCoffeeVarietyById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит удаление сорта кофе",
            example = "1",
            required = true
    ) @PathVariable Long id) {
        coffeeVarietyService.delete(id);
    }
}
