package com.ilyabogatskiy.coffee_shop.controllers;

import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("Получение списка всех позиций заказа")
    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return new ResponseEntity<>(orderService.getAllOrderItems(),
                HttpStatus.OK);
    }

    @ApiOperation("Получение списка заказов")
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllCoffeeVarieties() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @ApiOperation("Получение заказа по id")
    @GetMapping("/find/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
    }

    @ApiOperation("Создать новую позицию заказа")
    @PostMapping("/add")
    public ResponseEntity<OrderItem> addOrderItem(@ApiParam(value = "Позиция заказа")
                                                  @PathVariable Long id,
                                                  @RequestBody OrderItem orderItem) {
        return orderService.addOrderItem(id, orderItem)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation("Создать новый заказ")
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@ApiParam(value = "Заказ")
                                          @RequestBody Order order) {
        return new ResponseEntity<>(orderService.addOrder(order), HttpStatus.CREATED);
    }

    @ApiOperation("Обновление заказа")
    @PutMapping("/update")
    public ResponseEntity<Order> updateCoffeeVarieties(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
    }

    @ApiOperation("Удаление заказа по id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
