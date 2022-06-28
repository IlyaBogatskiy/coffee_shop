package com.ilyabogatskiy.coffee_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private LocalDateTime orderDateTime;

    private String customerName;

    private String deliveryAddress;

    private BigDecimal orderPrice;

    private List<OrderItemDto> orderItemDtos;
}
