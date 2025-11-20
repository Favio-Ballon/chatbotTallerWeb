package com.tallerWerb.chatbot.application.dto;

import java.math.BigDecimal;

public class ProductDto {
    private String name;
    private Double price;

    public ProductDto(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }

}
