package com.ilyabogatskiy.coffee_shop.controllers;

import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.OrderItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order_item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @ApiOperation("Получение списка всех позиций заказа")
    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return new ResponseEntity<>(orderItemService.getAllOrderItems(),
                HttpStatus.OK);
    }

    @ApiOperation("Создать новую позицию заказа")
    @PostMapping("/add")
    public ResponseEntity<OrderItem> addOrderItem(@ApiParam(value = "Позиция заказа")
                                                  @PathVariable Long id,
                                                  @RequestBody OrderItem orderItem) {
        return orderItemService.addOrderItem(id, orderItem)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
