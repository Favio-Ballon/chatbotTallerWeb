package com.tallerWerb.chatbot.infraestructure.controllers;

import com.tallerWerb.chatbot.application.dto.ChatRequestDto;
import com.tallerWerb.chatbot.application.dto.ChatResponseDto;
import com.tallerWerb.chatbot.application.port.in.ChatUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatUseCase chatUseCase;

    @Autowired(required = false)
    public ChatController(ChatUseCase chatUseCase) {
        this.chatUseCase = chatUseCase;
    }

    @PostMapping
    public ChatResponseDto chat(@RequestBody ChatRequestDto request) {
        return chatUseCase.handle(request);
    }
}
