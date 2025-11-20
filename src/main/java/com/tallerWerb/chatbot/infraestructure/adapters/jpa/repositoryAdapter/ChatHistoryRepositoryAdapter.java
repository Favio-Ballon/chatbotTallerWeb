package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.tallerWerb.chatbot.application.port.out.ChatHistoryRepository;
import com.tallerWerb.chatbot.domain.models.ChatHistory;
import com.tallerWerb.chatbot.infraestructure.adapters.entity.ChatHistoryEntity;
import com.tallerWerb.chatbot.infraestructure.adapters.jpa.jpaRepository.ChatHistoryJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatHistoryRepositoryAdapter implements ChatHistoryRepository {
    private final ChatHistoryJpaRepository jpaRepository;

    public ChatHistoryRepositoryAdapter(ChatHistoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<ChatHistory> findByUserKey(String userKey) {
        return jpaRepository.findByUserKey(userKey)
                .map(e -> new ChatHistory(e.getUserKey(), e.getMessagesJson()));
    }

    @Override
    public void save(ChatHistory history) {
        ChatHistoryEntity entity= new ChatHistoryEntity();
        entity.setUserKey(history.getUserKey());
        entity.setMessagesJson(history.getMessagesJson());
        jpaRepository.save(entity);
    }
}

