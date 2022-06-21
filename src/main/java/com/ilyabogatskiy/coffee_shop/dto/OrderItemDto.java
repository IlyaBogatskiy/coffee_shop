package com.ilyabogatskiy.coffee_shop.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Integer id;

    private CoffeeVarietyDto coffeeVarietyDto;

    private Integer cups;
}
