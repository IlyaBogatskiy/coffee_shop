package com.ilyabogatskiy.coffee_shop.exception;

public class CoffeeVarietyNotFoundException extends RuntimeException {

    public CoffeeVarietyNotFoundException(String message) {
        super(message);
    }
}
