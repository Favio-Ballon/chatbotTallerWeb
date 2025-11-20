package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.tallerWerb.chatbot.application.port.out.ChatEnginePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("externalChatEngine")
public class ExternalN8nChatEngineAdapter implements ChatEnginePort {
    private static final Logger logger = LoggerFactory.getLogger(ExternalN8nChatEngineAdapter.class);

    private final RestTemplate restTemplate;
    private final String n8nUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();
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

        String responseBody;
        try {
            ResponseEntity<String> entity = restTemplate.postForEntity(n8nUrl, body, String.class);
            responseBody = entity.getBody();
        } catch (RestClientException ex) {
            logger.error("Failed to call n8n at {}: {}", n8nUrl, ex.getMessage());
            return "";
        }

        if (responseBody == null || responseBody.isBlank()) {
            return "";
        }

        // Normalize and strip simple prefixes often returned by tools (e.g. "output\n", "output:")
        String normalized = responseBody.trim();
        normalized = normalized.replaceFirst("(?i)^(output|response)[:\\s\\n]+", "");

        // Unescape literal \n sequences (if payload contains backslash-n) to real newlines
        normalized = normalized.replace("\\n", "\n");

        // Try parse JSON body into a Map and extract 'response' key; otherwise return cleaned raw string
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            Map<String, Object> respMap = objectMapper.readValue(normalized, typeRef);
            Object respValue = respMap.get("response");
            if (respValue != null) {
                return respValue.toString();
            }
            // if no 'response' key, try common keys
            if (respMap.containsKey("output")) {
                return respMap.get("output").toString();
            }
            if (respMap.containsKey("message")) {
                return respMap.get("message").toString();
            }
            // fallback to the original normalized body
            return cleanText(normalized);
        } catch (JsonProcessingException e) {
            // Not JSON -> likely plain text or HTML. Return cleaned text.
            return cleanText(normalized);
        }
    }

    private String cleanText(String raw) {
        if (raw == null) return "";
        String s = raw.trim();
        // If it contains HTML tags, remove them
        if (s.contains("<") && s.contains(">")) {
            s = s.replaceAll("<[^>]*>", "").trim();
        }
        // Unescape a couple common HTML entities
        s = s.replace("&nbsp;", " ")
             .replace("&amp;", "&")
             .replace("&lt;", "<")
             .replace("&gt;", ">");
        // Normalize repeated blank lines
        s = s.replaceAll("(?m)(\\r?\\n){3,}", "\n\n");
        return s;
    }
}
