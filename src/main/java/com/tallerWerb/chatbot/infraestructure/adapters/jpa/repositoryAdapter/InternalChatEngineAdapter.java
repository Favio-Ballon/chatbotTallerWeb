package com.tallerWerb.chatbot.infraestructure.adapters.jpa.repositoryAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerWerb.chatbot.application.port.out.ChatEnginePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service("internalChatEngine")
public class InternalChatEngineAdapter implements ChatEnginePort {

    @Value("${google.api.key:}")
    private String googleApiKey;

    @Value("${google.gemini.model:gemini-1.5-flash}")
    private String geminiModel;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String generateAnswer(String userKey, String message, String contextJson) {

        String prompt = buildPrompt(userKey, message, contextJson);

        if (googleApiKey == null || googleApiKey.isBlank()) {
            return "[internal-demo] " + message;
        }

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        String url = "https://generativelanguage.googleapis.com/v1/models/"
                + geminiModel
                + ":generateContent?key="
                + googleApiKey;

        try {
            Map<?, ?> response = restTemplate.postForObject(url, body, Map.class);

            List<?> candidates = (List<?>) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return "No recibí respuesta del motor interno.";
            }

            Map<?, ?> first = (Map<?, ?>) candidates.get(0);
            Map<?, ?> content = (Map<?, ?>) first.get("content");
            List<?> parts = (List<?>) content.get("parts");
            if (parts == null || parts.isEmpty()) {
                return "No recibí contenido del motor interno.";
            }

            Map<?, ?> part0 = (Map<?, ?>) parts.get(0);
            Object text = part0.get("text");
            return text != null ? text.toString() : "Respuesta vacía del motor interno.";
        } catch (Exception e) {
            return "Ocurrió un error al conectarse con el motor interno de IA.";
        }
    }

    private String buildPrompt(String userKey, String message, String contextJson) {
        String prettyContext = contextJson;
        try {
            Object parsed = mapper.readValue(contextJson, Object.class);
            prettyContext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(parsed);
        } catch (JsonProcessingException ignored) {
        }

        return """
                Eres un chatbot interno de un sistema de productos.
                Respondes en espanol, de forma clara, amable y breve.

                ID de usuario: %s

                CONTEXTO (historial + productos en formato JSON):
                %s

                Mensaje actual del usuario:
                %s

                Respuesta:
                """.formatted(userKey, prettyContext, message);
    }
}
