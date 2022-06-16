package com.ilyabogatskiy.coffee_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @GetMapping("/")
    public String common() {
        return "greeting";
    }

    @GetMapping("/order_item")
    public String orderItem() {
        return "order-item";
    }

    @GetMapping("/stock_information")
    public String stockInformation() {
        return "stock-information";
    }

    @GetMapping("/create_order")
    public String newOrder() {
        return "create-order";
    }

    @GetMapping("/complete_order")
    public String completeOrder() {
        return "complete";
    }
}