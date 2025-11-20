package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.tallerWerb.chatbot.application.port.out.ChatEnginePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("externalChatEngine")
public class ExternalN8nChatEngineAdapter implements ChatEnginePort {
    private static final Logger logger = LoggerFactory.getLogger(ExternalN8nChatEngineAdapter.class);

    private final RestTemplate restTemplate;
    private final String n8nUrl;
    @Autowired
    public ExternalN8nChatEngineAdapter(RestTemplate restTemplate,
                                        @Value("${chat.n8n.url}") String n8nUrl) {
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

        Map resp = null;
        try {
            resp = restTemplate.postForObject(n8nUrl, body, Map.class);
        } catch (RestClientException ex) {
            logger.error("Failed to call n8n at {}: {}", n8nUrl, ex.getMessage());
            return "";
        }

        if (resp == null) {
            return "";
        }

        Object respValue = resp.get("response");
        return respValue != null ? respValue.toString() : "";
    }
}
