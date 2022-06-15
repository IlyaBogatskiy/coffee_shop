package com.ilyabogatskiy.coffee_shop.controllers;

import com.ilyabogatskiy.coffee_shop.service.interfaces.CoffeeVarietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final CoffeeVarietyService coffeeVarietyService;

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", coffeeVarietyService.getAllCoffeeVarieties());
        return "products";
    }
}
