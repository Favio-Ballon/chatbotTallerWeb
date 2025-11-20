package com.tallerWerb.chatbot.infraestructure.adapters.jpa.mapper;

import com.tallerWerb.chatbot.domain.models.Product;
import com.tallerWerb.chatbot.infraestructure.adapters.entity.ProductoEntity;

public class ProductMapper {

    public Product offEntityToModel(ProductoEntity productoEntity){
        Product product = null;
        if(productoEntity != null){
            product = new Product(
                    productoEntity.getId(),
                    productoEntity.getName(),
                    productoEntity.getPrice()
            );
            return product;
        }

    }
}
