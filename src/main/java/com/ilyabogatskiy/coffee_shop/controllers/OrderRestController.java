package com.ilyabogatskiy.coffee_shop.controllers;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Order Rest Controller")
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @ApiOperation(value = "getAllOrders", notes = "Получение списка заказов")
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @ApiOperation(value = "getOrderById", notes = "Получение заказа по id")
    @GetMapping("/find/{id}")
    public ResponseEntity<Order> getOrderById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит поиск заказа",
            example = "1",
            required = true)
                                              @PathVariable Long id) {
        return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "createOrder", notes = "Создать новый заказ")
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@ApiParam(value = "Создание нового заказа")
                                          @RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @ApiOperation(value = "createOrderItem", notes = "Создать новый заказ")
    @PostMapping("/create_item")
    public ResponseEntity<OrderItem> createOrderItem(@ApiParam(value = "Создание новой позиции заказа")
                                          @RequestBody OrderItem orderItem) {
        return new ResponseEntity<>(orderService.createOrderItem(orderItem), HttpStatus.CREATED);
    }

    @ApiOperation(value = "updateOrderById", notes = "Обновление заказа")
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrderById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит поиск заказа для последующего изменения",
            example = "1",
            required = true)
                                                 @PathVariable Long id) {
        return new ResponseEntity<>(orderService.updateOrderById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "deleteOrderById", notes = "Удаление заказа по id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrderById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id, по которому происходит удаление заказа",
            example = "1",
            required = true)
                                             @PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
