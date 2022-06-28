package com.ilyabogatskiy.coffee_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeVarietyDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private Boolean available;
}
