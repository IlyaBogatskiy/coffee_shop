package com.ilyabogatskiy.coffee_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long id;

    private Long coffeeVarietyId;

    private Integer cups;

    private BigDecimal orderItemPrice;
}
