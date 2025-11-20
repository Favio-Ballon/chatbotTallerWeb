package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.tallerWerb.chatbot.application.dto.ProductDto;
import com.tallerWerb.chatbot.application.port.out.ProductRepository;
import com.tallerWerb.chatbot.domain.models.Product;
import com.tallerWerb.chatbot.infraestructure.adapters.entity.ProductoEntity;
import com.tallerWerb.chatbot.infraestructure.adapters.jpa.jpaRepository.ProductoJpaRepository;
import com.tallerWerb.chatbot.infraestructure.adapters.jpa.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoRepositoryAdapter implements ProductRepository {
    private final ProductoJpaRepository productoJpaRepository;

    public ProductoRepositoryAdapter(ProductoJpaRepository productoJpaRepository) {
        this.productoJpaRepository = productoJpaRepository;
    }


    private final ProductMapper productMapper = new ProductMapper();
    @Override
    public List<Product> findAll() {
        List<ProductoEntity> products = productoJpaRepository.findAll();
        return products.stream().map(productMapper::offEntityToModel).toList();
    }

    @Override
    public Product save(ProductDto product) {
        ProductoEntity productoEntity = productMapper.ofDtotoEntity(product);
        productoJpaRepository.save(productoEntity);
        return productMapper.offEntityToModel(productoEntity);
    }
}
