package com.ilyabogatskiy.coffee_shop.controller;

import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.dto.mapper.OrderItemMapper;
import com.ilyabogatskiy.coffee_shop.dto.mapper.OrderMapper;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.service.OrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Api(tags = "order-rest-controller")
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/all")
    @ApiOperation(value = "Получение списка заказов")
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderService.findAll();
        return orderMapper.toDto(orders);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение заказа по id")
    public OrderDto getOrderById(
            @ApiParam(value = "order_id", required = true) @RequestParam Long id
    ) {
        Order order = orderService.findById(id);
        return orderMapper.toDto(order);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Создать новый заказ")
    public OrderDto createOrder(
            @ApiParam(value = "Создание нового заказа") @RequestBody OrderDto orderDto
    ) {
        Order order = orderService.add(orderMapper.toModel(orderDto));
        return orderMapper.toDto(order);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление заказа по id")
    public void deleteOrderById(
            @ApiParam(value = "order_id", required = true) @RequestParam Long id
    ) {
        orderService.delete(id);
    }
}
