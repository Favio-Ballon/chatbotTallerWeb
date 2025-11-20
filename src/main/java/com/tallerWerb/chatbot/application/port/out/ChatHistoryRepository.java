package com.tallerWerb.chatbot.application.port.out;

import com.tallerWerb.chatbot.domain.models.ChatHistory;

import java.util.Optional;

public interface ChatHistoryRepository {
    Optional<ChatHistory> findByUserKey(String userKey);
    void save(ChatHistory history);
}
