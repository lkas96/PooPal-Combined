package com.lask.poopal_server.poopal_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lask.poopal_server.poopal_server.models.ChatMessage;
import com.lask.poopal_server.poopal_server.services.JedisChatService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/chat")
public class ChatSocketController {

    @Autowired JedisChatService jcs;

    @MessageMapping("/chat") // from angular frontend
    @SendTo("/topic/messages") // to subscribers
    public ChatMessage sendMessage(ChatMessage message) {
        message.setTimestamp(System.currentTimeMillis());
        jcs.sendMessage(message); // save to Redis
        return message;
    }

    
}
