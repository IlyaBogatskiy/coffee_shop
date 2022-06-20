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

    @ApiOperation(value = "getAllOrderItems", notes = "Получение списка всех позиций заказа")
    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return new ResponseEntity<>(orderService.getAllOrderItems(),
                HttpStatus.OK);
    }

    @ApiOperation(value = "getAllOrders", notes = "Получение списка заказов")
    @GetMapping("/item/all")
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

    @ApiOperation(value = "addOrderItem", notes = "Создать новую позицию заказа")
    @PostMapping("/item/add")
    public ResponseEntity<OrderItem> addOrderItemWithoutOrder(@ApiParam(value = "Новая позиция заказа без заказа")
                                                              @RequestBody OrderItem orderItem) {
        return new ResponseEntity<>(orderService.addOrderItemWithoutOrder(orderItem), HttpStatus.CREATED);
    }

    @ApiOperation(value = "addOrderItem", notes = "Создать новую позицию заказа")
    @PostMapping("/{id}/item/add")
    public ResponseEntity<OrderItem> addOrderItem(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный в URL id заказа, по которому происходит cоздание новой позиции заказа",
            example = "1",
            required = true)
                                                  @PathVariable Long id,
                                                  @RequestBody OrderItem orderItem) {
        return orderService.addOrderItem(id, orderItem)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "addOrder", notes = "Создать новый заказ")
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@ApiParam(value = "Новый заказ")
                                          @RequestBody Order order) {
        return new ResponseEntity<>(orderService.addOrder(order), HttpStatus.CREATED);
    }

    @ApiOperation(value = "updateOrder", notes = "Обновление заказа")
    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
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
