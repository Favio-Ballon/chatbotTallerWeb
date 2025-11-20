package com.tallerWerb.chatbot.application.services;

import com.tallerWerb.chatbot.application.port.out.ProductRepository;
import com.tallerWerb.chatbot.domain.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }


}
