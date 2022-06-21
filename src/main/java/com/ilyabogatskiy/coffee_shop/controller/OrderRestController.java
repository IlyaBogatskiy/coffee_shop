package com.ilyabogatskiy.coffee_shop.controller;

import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.dto.OrderItemDto;
import com.ilyabogatskiy.coffee_shop.mapper.OrderItemMapper;
import com.ilyabogatskiy.coffee_shop.mapper.OrderMapper;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.Impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Order Rest Controller")
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderServiceImpl orderService;

    @ApiOperation(value = "getAllOrders", notes = "Получение списка заказов")
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @ApiOperation(value = "getOrderById", notes = "Получение заказа по id")
    @GetMapping("/find/{id}")
    public Order getOrderById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит поиск заказа",
            example = "1",
            required = true
    ) @PathVariable Long id, @RequestBody OrderDto orderDto) {
        Order order = Mappers.getMapper(OrderMapper.class).toModel(orderDto);
        orderService.findById(id);
        return order;
    }

    @ApiOperation(value = "createOrder", notes = "Создать новый заказ")
    @PostMapping("/create")
    public Order createOrder(@ApiParam(value = "Создание нового заказа")
                             @RequestBody OrderDto orderDto) {
        Order order = Mappers.getMapper(OrderMapper.class).toModel(orderDto);
        orderService.add(order);
        return order;
    }

    @ApiOperation(value = "createOrderItem", notes = "Создать новую позицию заказа")
    @PostMapping("/create_item")
    public OrderItem createOrderItem(@ApiParam(value = "Создание новой позиции заказа")
                                     @RequestBody OrderItemDto orderItemDto) {
        OrderItem orderItem = Mappers.getMapper(OrderItemMapper.class).toModel(orderItemDto);
        orderService.addItem(orderItem);
        return orderItem;
    }

    @ApiOperation(value = "updateCoffeeVarieties", notes = "Обновление заказа")
    @PutMapping("/update")
    public Order updateOrder(@RequestBody OrderDto orderDto) {
        Order order = Mappers.getMapper(OrderMapper.class).toModel(orderDto);
        orderService.edit(order);
        return order;
    }

    @ApiOperation(value = "deleteOrderById", notes = "Удаление заказа по id")
    @DeleteMapping("/delete/{id}")
    public Order deleteOrderById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит удаление заказа",
            example = "1",
            required = true
    ) @PathVariable Long id, @RequestBody OrderDto orderDto) {
        Order order = Mappers.getMapper(OrderMapper.class).toModel(orderDto);
        orderService.delete(id);
        return order;
    }
}
