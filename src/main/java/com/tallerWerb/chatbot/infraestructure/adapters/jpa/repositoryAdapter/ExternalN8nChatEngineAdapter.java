package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.tallerWerb.chatbot.application.port.out.ChatEnginePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("externalChatEngine")
public class ExternalN8nChatEngineAdapter implements ChatEnginePort {
    private final RestTemplate restTemplate;
    private final String n8nUrl;
    @Autowired(required = false)
    public ExternalN8nChatEngineAdapter(RestTemplate restTemplate, String n8nUrl) {
        this.restTemplate = restTemplate;
        this.n8nUrl = n8nUrl;
    }
    @Override
    public String generateAnswer(String userKey, String message, String contextJson) {
        Map<String, Object> body = Map.of(
                "userKey", userKey,
                "message", message,
                "context", contextJson
        );

        Map resp = restTemplate.postForObject(n8nUrl, body, Map.class);
        if (resp == null) {
            return "";
        }

        Object respValue = resp.get("response");
        return respValue != null ? respValue.toString() : "";
    }
}
