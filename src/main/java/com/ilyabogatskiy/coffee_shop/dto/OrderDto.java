package com.ilyabogatskiy.coffee_shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long id;

    private String orderDateTime;

    private String customerName;

    private String deliveryAddress;

    private String orderPrice;

    private List<OrderItemDto> orderItemDtos;
}
