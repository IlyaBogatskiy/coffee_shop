package com.ilyabogatskiy.coffee_shop.dto.mapper;

import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "orderItemDtos", source = "orderItems"),
            @Mapping(source = "orderPrice", target = "orderPrice", numberFormat = "#.##E0")
    })
    OrderDto toDto(Order order);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "orderItems", source = "orderItemDtos"),
            @Mapping(target = "orderPrice", ignore = true)
    })
    Order toModel(OrderDto orderDto);

    List<OrderDto> toDto(List<Order> all);
}
