package com.lask.poopal_server.poopal_server.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lask.poopal_server.poopal_server.models.ChatMessage;
import com.lask.poopal_server.poopal_server.repository.JedisChatRepo;

@Service
public class JedisChatService {

    @Autowired JedisChatRepo jcr;

    public void sendMessage(ChatMessage message) {
        jcr.sendMessage(message);
    }

    public List<ChatMessage> getMessages() {
        return jcr.getMessages();
    }
}
