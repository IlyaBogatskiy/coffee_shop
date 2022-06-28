package com.ilyabogatskiy.coffee_shop.mapper;

import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "orderItemDtos", source = "orderItems")
    OrderDto toDto(Order order);

    @Mapping(target = "orderItems", source = "orderItemDtos")
    Order toModel(OrderDto orderDto);

    List<OrderDto> toDto(List<Order> all);
}
