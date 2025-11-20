package com.tallerWerb.chatbot.infraestructure.adapters.jpa.jpaRepository;

import com.tallerWerb.chatbot.infraestructure.adapters.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoJpaRepository extends JpaRepository<ProductoEntity, Long> {

}
