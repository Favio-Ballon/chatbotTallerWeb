package com.tallerWerb.chatbot.application.services;

import com.tallerWerb.chatbot.application.dto.ChatRequestDto;
import com.tallerWerb.chatbot.application.dto.ChatResponseDto;
import com.tallerWerb.chatbot.application.dto.ProductDto;
import com.tallerWerb.chatbot.application.port.in.ChatUseCase;
import com.tallerWerb.chatbot.application.port.out.ChatEnginePort;
import com.tallerWerb.chatbot.application.port.out.ChatHistoryRepository;
import com.tallerWerb.chatbot.application.port.out.ProductRepository;
import com.tallerWerb.chatbot.domain.models.ChatHistory;
import com.tallerWerb.chatbot.domain.models.Product;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService implements ChatUseCase {
    private final ProductRepository productRepository;
    private final ChatHistoryRepository historyRepository;
    private final ChatEnginePort chatEngine;

    public ChatService(ProductRepository productRepository, ChatHistoryRepository historyRepository, ChatEnginePort chatEngine) {
        this.productRepository = productRepository;
        this.historyRepository = historyRepository;
        this.chatEngine = chatEngine;
    }

    @Override
    public ChatResponseDto handle(ChatRequestDto request) {
        String userKey = request.getUserKey();
        String message = request.getMessage();

        List<Product> products = productRepository.findAll();

        String contextJson = convertProductsToJson(products);

        System.out.println("Context JSON: " + contextJson);

        String answer = chatEngine.generateAnswer(userKey, message, contextJson);

        historyRepository.save(new ChatHistory(userKey, answer));

        return new ChatResponseDto(answer);
    }

    private String convertProductsToJson(List<Product> products) {
        try {
            List<ProductDto> dtoList = new ArrayList<>();
            if (products != null) {
                for (Product p : products) {
                    dtoList.add(new ProductDto(p.getName(), p.getPrice()));
                }
            }
            Map<String, Object> ctx = Map.of("products", dtoList);
            return new ObjectMapper().writeValueAsString(ctx);
        } catch (Exception e) {
            return "{}";
        }
    }
}
