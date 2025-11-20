package com.tallerWerb.chatbot.infraestructure.adapters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userKey;

    @Column(columnDefinition = "TEXT")
    private String messagesJson;
}
