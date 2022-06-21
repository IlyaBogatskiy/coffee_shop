package com.ilyabogatskiy.coffee_shop.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long id;

    private CoffeeVarietyDto coffeeVarietyDto;

    private Integer cups;
}
