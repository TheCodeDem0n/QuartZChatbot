package com.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.miniproject.entities.ChatMessage;
import com.miniproject.services.ChatService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    //Send Message
    @PostMapping("/send/{sessionId}")
    public Map<String, String> sendMessage(@PathVariable Long sessionId,
                                           @RequestBody ChatMessage msg) {
        return chatService.sendMessage(sessionId, msg);
    }

    //Get Messages
    @GetMapping("/messages/{sessionId}")
    public List<ChatMessage> getMessages(@PathVariable Long sessionId) {
        return chatService.getMessages(sessionId);
    }
}
