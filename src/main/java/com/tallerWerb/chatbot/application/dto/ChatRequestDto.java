package com.tallerWerb.chatbot.application.dto;

public class ChatRequestDto {
    private String userKey;
    private String message;

    public ChatRequestDto(String userKey, String message) {
        this.userKey = userKey;
        this.message = message;
    }
    public String getUserKey() {
        return userKey;
    }
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
