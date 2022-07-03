package com.ilyabogatskiy.coffee_shop.dto.mapper;

import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoffeeVarietyMapper {

    CoffeeVarietyDto toDto(CoffeeVariety coffeeVariety);

    CoffeeVariety toModel(CoffeeVarietyDto coffeeVarietyDto);

    List<CoffeeVarietyDto> toDto(List<CoffeeVariety> all);
}
