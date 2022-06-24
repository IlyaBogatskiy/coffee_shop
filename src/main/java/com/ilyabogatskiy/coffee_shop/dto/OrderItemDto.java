package com.ilyabogatskiy.coffee_shop.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long id;

    private Long coffeeVarietyId;

    private Integer cups;
}
