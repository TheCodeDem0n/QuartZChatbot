package com.miniproject.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.miniproject.entities.ChatSession;
import com.miniproject.services.ChatSessionService;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatSessionController {

    @Autowired
    private ChatSessionService sessionService;

    //Create new chat session for a user
    @PostMapping("/newSession/{username}")
    public ChatSession createNewSession(@PathVariable String username) {
        return sessionService.createNewSession(username);
    }

    //Get latest chat session for a user
    @GetMapping("/latest/{username}")
    public ChatSession getLatestSession(@PathVariable String username) {
        return sessionService.getLatestSession(username);
    }

    //Get all sessions for a user
    @GetMapping("/sessions/{username}")
    public List<ChatSession> getUserSessions(@PathVariable String username) {
        return sessionService.getUserSessions(username);
    }
}
