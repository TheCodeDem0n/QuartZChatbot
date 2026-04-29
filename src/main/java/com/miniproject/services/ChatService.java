package com.miniproject.services;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.entities.ChatMessage;
import com.miniproject.entities.ChatSession;
import com.miniproject.repositories.ChatRepository;
import com.miniproject.repositories.ChatSessionRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepo;

    @Autowired
    private ChatSessionRepository sessionRepo;

    @Autowired
    private GeminiService geminiService;

    //Send Message with Gemini
    public Map<String, String> sendMessage(Long sessionId, ChatMessage msg) {

        ChatSession session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found!"));

        //Load full history of this session
        List<ChatMessage> history = chatRepo.findBySessionId(sessionId);

        //Generate AI response using full history
        String botResponse = geminiService.generateResponse(history, msg.getMessage());

        //Save new message
        msg.setSession(session);
        msg.setTimestamp(LocalDateTime.now());
        msg.setResponse(botResponse);

        chatRepo.save(msg);

        //Update session title for new sessions
        if (session.getTitle() == null || session.getTitle().startsWith("Chat")) {
            if (msg.getMessage().length() > 25) {
                session.setTitle(msg.getMessage().substring(0, 25) + "...");
            } else {
                session.setTitle(msg.getMessage());
            }
            sessionRepo.save(session);
        }

        Map<String, String> res = new HashMap<>();
        res.put("response", botResponse);
        return res;
    }

    //Get Messages
    public List<ChatMessage> getMessages(Long sessionId) {
        return chatRepo.findBySessionId(sessionId);
    }
}
