package com.ilyabogatskiy.coffee_shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CoffeeVarietyNotFoundException extends RuntimeException {

    public CoffeeVarietyNotFoundException(String message) {
        super(message);
    }
}
