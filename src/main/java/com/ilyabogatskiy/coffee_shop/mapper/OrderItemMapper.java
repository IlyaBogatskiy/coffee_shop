package com.ilyabogatskiy.coffee_shop.mapper;

import com.ilyabogatskiy.coffee_shop.dto.OrderItemDto;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CoffeeVarietyService.class})
public interface OrderItemMapper {

    @Mapping(target = "coffeeVarietyId", source = "coffeeVariety.id")
    OrderItemDto toDto(OrderItem orderItem);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "coffeeVariety.id", source = "coffeeVarietyId")
    })
    OrderItem toModel(OrderItemDto orderItemDto);
}
