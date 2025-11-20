package com.tallerWerb.chatbot.application.dto;

public class ChatResponseDto {
    private String message;

    public ChatResponseDto(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
