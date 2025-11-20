package com.tallerWerb.chatbot.domain.models;

public class ChatHistory {
    private final String userKey;
    private final String messagesJson;

    public ChatHistory(String userKey, String messagesJson) {
        this.userKey = userKey;
        this.messagesJson = messagesJson;
    }
    public String getUserKey() {
        return userKey;
    }
    public String getMessagesJson() {
        return messagesJson;
    }

}
