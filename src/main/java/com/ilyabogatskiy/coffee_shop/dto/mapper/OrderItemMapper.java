package com.ilyabogatskiy.coffee_shop.dto.mapper;

import com.ilyabogatskiy.coffee_shop.dto.OrderItemDto;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CoffeeVarietyService.class})
public interface OrderItemMapper {

    @Mappings({
            @Mapping(target = "coffeeVarietyId", source = "coffeeVariety.id"),
            @Mapping(source = "orderItemPrice", target = "orderItemPrice", numberFormat = "#.##E0")
    })
    OrderItemDto toDto(OrderItem orderItem);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "coffeeVariety.id", source = "coffeeVarietyId"),
            @Mapping(target = "orderItemPrice", ignore = true)
    })
    OrderItem toModel(OrderItemDto orderItemDto);
}
