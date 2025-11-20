package com.tallerWerb.chatbot.infraestructure.adapters.jpa.jpaRepository;

import com.tallerWerb.chatbot.infraestructure.adapters.entity.ChatHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatHistoryJpaRepository extends JpaRepository<ChatHistoryEntity, Long> {
    Optional<ChatHistoryEntity> findByUserKey(String userKey);
}

