package com.tallerWerb.chatbot.application.port.out;

import com.tallerWerb.chatbot.domain.models.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

}
