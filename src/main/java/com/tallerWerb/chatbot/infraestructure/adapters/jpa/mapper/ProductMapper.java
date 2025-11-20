package com.tallerWerb.chatbot.infraestructure.adapters.jpa.mapper;

import com.tallerWerb.chatbot.application.dto.ProductDto;
import com.tallerWerb.chatbot.domain.models.Product;
import com.tallerWerb.chatbot.infraestructure.adapters.entity.ProductoEntity;

public class ProductMapper {

    public Product offEntityToModel(ProductoEntity productoEntity){
        Product product = null;
            product = new Product(
                    productoEntity.getId(),
                    productoEntity.getName(),
                    productoEntity.getPrice()
            );
            return product;
    }
    public ProductoEntity ofModelToEntity(Product product){
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setId(product.getId());
        productoEntity.setName(product.getName());
        productoEntity.setPrice(product.getPrice());
        return productoEntity;
    }

    public ProductoEntity ofDtotoEntity(ProductDto productDto){
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setName(productDto.getName());
        productoEntity.setPrice(productDto.getPrice());
        return productoEntity;
    }
}
