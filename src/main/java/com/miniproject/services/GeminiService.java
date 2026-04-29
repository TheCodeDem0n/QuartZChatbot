package com.miniproject.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniproject.entities.ChatMessage;

import java.util.*;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateResponse(List<ChatMessage> history, String userMessage) {

        List<Map<String, Object>> contents = new ArrayList<>();

        //Build conversation history
        for (ChatMessage msg : history) {

            //User's past message
            contents.add(Map.of(
                    "role", "user",
                    "parts", List.of(Map.of("text", msg.getMessage()))
            ));

            //Bot's past reply
            if (msg.getResponse() != null) {
                contents.add(Map.of(
                        "role", "model",
                        "parts", List.of(Map.of("text", msg.getResponse()))
                ));
            }
        }

        //Current user message
        contents.add(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", userMessage))
        ));

        Map<String, Object> requestBody = Map.of("contents", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    apiUrl,                       
                    new HttpEntity<>(requestBody, headers),
                    Map.class
            );

            Map body = response.getBody();
            if (body == null || body.get("candidates") == null) {
                return "AI Error: empty response from Gemini";
            }

            Map candidate = (Map) ((List) body.get("candidates")).get(0);
            Map content = (Map) candidate.get("content");
            List<Map> parts = (List<Map>) content.get("parts");

            Object text = parts.get(0).get("text");
            return text != null ? text.toString() : "AI Error: no text in response";

        } catch (Exception e) {
            e.printStackTrace();
            return "AI Error: " + e.getMessage();
        }
    }
}
