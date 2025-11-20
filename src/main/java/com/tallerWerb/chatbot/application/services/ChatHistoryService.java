package com.tallerWerb.chatbot.application.services;

import com.tallerWerb.chatbot.application.port.out.ChatHistoryRepository;
import com.tallerWerb.chatbot.domain.models.ChatHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatHistoryService {
    private final ChatHistoryRepository chatHistoryRepository;
    @Autowired(required = false)
    public ChatHistoryService(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
    }
    public void save(ChatHistory history){
        chatHistoryRepository.save(history);
    }

}
