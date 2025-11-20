package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.tallerWerb.chatbot.application.port.out.ProductRepository;
import com.tallerWerb.chatbot.domain.models.Product;
import com.tallerWerb.chatbot.infraestructure.adapters.entity.ProductoEntity;
import com.tallerWerb.chatbot.infraestructure.adapters.jpa.jpaRepository.ProductoJpaRepository;
import com.tallerWerb.chatbot.infraestructure.adapters.jpa.mapper.ProductMapper;

import java.util.List;

public class ProductoRepositoryAdapter implements ProductRepository {
    private final ProductoJpaRepository productoJpaRepository;
    private final ProductMapper productMapper = new ProductMapper();
    public ProductoRepositoryAdapter(ProductoJpaRepository productoJpaRepository) {
        this.productoJpaRepository = productoJpaRepository;
    }
    @Override
    public List<Product> findAll() {
        List<ProductoEntity> products = productoJpaRepository.findAll();
        return products.stream().map(productMapper::offEntityToModel).toList();
    }
}
