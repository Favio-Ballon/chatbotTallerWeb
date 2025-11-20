package com.tallerWerb.chatbot.application.port.in;

import com.tallerWerb.chatbot.application.dto.ChatRequestDto;
import com.tallerWerb.chatbot.application.dto.ChatResponseDto;

public interface ChatUseCase {
    ChatResponseDto handle(ChatRequestDto request);
}
