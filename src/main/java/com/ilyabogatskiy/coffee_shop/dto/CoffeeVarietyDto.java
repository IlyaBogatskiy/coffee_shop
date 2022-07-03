package com.ilyabogatskiy.coffee_shop.dto;

import lombok.Data;

@Data
public class CoffeeVarietyDto {

    private Long id;

    private String name;

    private String price;

    private Boolean available;
}
