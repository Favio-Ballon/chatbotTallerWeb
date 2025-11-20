package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.tallerWerb.chatbot.application.port.out.ChatEnginePort;
import org.springframework.stereotype.Service;

@Service("internalChatEngine")
public class InternalChatEngineAdapter implements ChatEnginePort {

    @Override
    public String generateAnswer(String userKey, String message, String contextJson) {
        // Basic internal engine implementation (placeholder)
        return "[internal] " + message;
    }
}

