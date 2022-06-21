package com.ilyabogatskiy.coffee_shop.models;

import lombok.Data;

@Data
public class Response {

    private String message;
    private String problem;

    public Response() {
    }

    public Response(String message, String problem) {
        this.message = message;
        this.problem = problem;
    }

    public Response(String message) {
        this.message = message;
    }
}
