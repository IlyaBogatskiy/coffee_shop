package com.ilyabogatskiy.coffee_shop.controller;

import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.dto.OrderItemDto;
import com.ilyabogatskiy.coffee_shop.mapper.OrderItemMapper;
import com.ilyabogatskiy.coffee_shop.mapper.OrderMapper;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.OrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Order Rest Controller")
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @GetMapping("/all")
    @ApiOperation(value = "getAllOrders", notes = "Получение списка заказов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список заказов успешно получен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные получены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public List<OrderDto> getAllOrders() {
        return orderMapper.toDto(orderService.findAll());
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "getOrderById", notes = "Получение заказа по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Заказ успешно найден"),
            @ApiResponse(code = 201, message = "Запрос принят и данные найдены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public Order getOrderById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит поиск заказа",
            example = "1",
            required = true
    ) @PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "createOrder", notes = "Создать новый заказ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Заказ успешно создан"),
            @ApiResponse(code = 201, message = "Запрос принят и данные созданы"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public OrderDto createOrder(@ApiParam(value = "Создание нового заказа")
                                @RequestBody OrderDto orderDto) {
        Order order = orderService.add(orderMapper.toModel(orderDto));
        return orderMapper.toDto(order);
    }

    @PostMapping("/create_item")
    @ApiOperation(value = "createOrderItem", notes = "Создать новую позицию заказа")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Позиция заказа успешно создана"),
            @ApiResponse(code = 201, message = "Запрос принят и данные созданы"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public OrderItemDto createOrderItem(@ApiParam(value = "Создание новой позиции заказа")
                                        @RequestBody OrderItemDto orderItemDto) {
        OrderItem orderItem = orderService.addItem(orderItemMapper.toModel(orderItemDto));
        return orderItemMapper.toDto(orderItem);
    }

    @ApiOperation(value = "updateOrder", notes = "Обновление заказа")
    @PutMapping("/update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Заказ успешно обновлен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные обновлены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.edit(orderMapper.toModel(orderDto));
        return orderMapper.toDto(order);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "deleteOrderById", notes = "Удаление заказа по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Заказ успешно удален"),
            @ApiResponse(code = 201, message = "Запрос принят и данные удалены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")
    })
    public void deleteOrderById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит удаление заказа",
            example = "1",
            required = true
    ) @PathVariable Long id) {
        orderService.delete(id);
    }
}
