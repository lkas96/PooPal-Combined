package com.lask.poopal_server.poopal_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lask.poopal_server.poopal_server.models.ChatMessage;
import com.lask.poopal_server.poopal_server.services.JedisChatService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/jedis/chat")
public class JedisChatController {

    @Autowired JedisChatService jcs;

    //send message to people/store on redis 15 mins basically
    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessage message) {
        message.setTimestamp(System.currentTimeMillis());
        jcs.sendMessage(message);
    }

    //pull all messages havent expired 15 mins
    @GetMapping("/messages")
    public List<ChatMessage> getMessages() {
        return jcs.getMessages();
    }
}
