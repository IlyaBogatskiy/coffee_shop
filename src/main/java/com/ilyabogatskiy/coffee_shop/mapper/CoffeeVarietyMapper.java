package com.ilyabogatskiy.coffee_shop.mapper;

import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeVarietyMapper {

    CoffeeVarietyDto toDto(CoffeeVariety coffeeVariety);

    CoffeeVariety toModel(CoffeeVarietyDto coffeeVarietyDto);
}
