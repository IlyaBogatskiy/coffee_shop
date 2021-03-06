package com.ilyabogatskiy.coffee_shop.dto.mapper;

import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "orderItems", source = "orderItems"),
            @Mapping(source = "orderPrice", target = "orderPrice", numberFormat = "#.00 TGR")
    })
    OrderDto toDto(Order order);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "orderItems", source = "orderItems"),
            @Mapping(target = "orderPrice", ignore = true)
    })
    Order toModel(OrderDto orderDto);
}
