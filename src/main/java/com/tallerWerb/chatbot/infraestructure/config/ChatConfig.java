package com.tallerWerb.chatbot.infraestructure.config;

import com.tallerWerb.chatbot.application.port.in.ChatUseCase;
import com.tallerWerb.chatbot.application.port.out.ChatEnginePort;
import com.tallerWerb.chatbot.application.port.out.ChatHistoryRepository;
import com.tallerWerb.chatbot.application.port.out.ProductRepository;
import com.tallerWerb.chatbot.application.services.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ChatConfig {

    @Value("${chat.engine:internal}")   // internal | external
    private String engineType;

    @Bean
    public ChatUseCase chatUseCase(ProductRepository productRepo,
                                   ChatHistoryRepository historyRepo,
                                   Map<String, ChatEnginePort> chatEngines) {

        ChatEnginePort engine = null;
        if ("external".equalsIgnoreCase(engineType) && chatEngines.containsKey("externalChatEngine")) {
            engine = chatEngines.get("externalChatEngine");
        } else if (chatEngines.containsKey("internalChatEngine")) {
            engine = chatEngines.get("internalChatEngine");
        } else if (!chatEngines.isEmpty()) {
            // fallback to any available ChatEnginePort
            engine = chatEngines.values().iterator().next();
        }

        return new ChatService(productRepo, historyRepo, engine);
    }
}
