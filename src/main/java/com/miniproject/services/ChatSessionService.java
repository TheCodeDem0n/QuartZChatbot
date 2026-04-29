package com.miniproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.entities.ChatSession;
import com.miniproject.repositories.ChatSessionRepository;

@Service
public class ChatSessionService {

    @Autowired
    private ChatSessionRepository sessionRepo;

    //Create new session
    public ChatSession createNewSession(String username) {
        ChatSession session = new ChatSession();
        session.setUsername(username);

        // Set default title
        int count = sessionRepo.findByUsername(username).size();
        session.setTitle("Chat " + (count + 1));

        return sessionRepo.save(session);
    }

    //Get latest session
    public ChatSession getLatestSession(String username) {
        List<ChatSession> sessions = sessionRepo.findByUsername(username);
        if (sessions.isEmpty()) return null;
        return sessions.get(sessions.size() - 1);
    }

    //Get all sessions
    public List<ChatSession> getUserSessions(String username) {
        return sessionRepo.findByUsername(username);
    }
}
