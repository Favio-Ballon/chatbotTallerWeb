package com.tallerWerb.chatbot.infraestructure.controllers;

import com.tallerWerb.chatbot.application.dto.ProductDto;
import com.tallerWerb.chatbot.application.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object getAllProducts() {
        return productService.findAll();
    }

    @PostMapping
    public Object saveProduct(@RequestBody ProductDto product){
        return productService.save(product);
    }


}
