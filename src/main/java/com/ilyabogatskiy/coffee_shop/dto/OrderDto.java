package com.ilyabogatskiy.coffee_shop.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long id;

    private LocalDateTime orderDate;

    private String customerName;

    private String deliveryAddress;

    private String orderPrice;

    private List<OrderItemDto> orderItemDtos;
}
