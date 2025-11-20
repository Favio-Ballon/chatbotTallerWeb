package com.tallerWerb.chatbot.application.port.out;

public interface ChatEnginePort {
    String generateAnswer(String userKey, String message, String contextJson);
}
