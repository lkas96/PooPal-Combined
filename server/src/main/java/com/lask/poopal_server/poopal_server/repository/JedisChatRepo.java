package com.lask.poopal_server.poopal_server.repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.lask.poopal_server.poopal_server.models.ChatMessage;

@Repository
public class JedisChatRepo {

    @Autowired RedisTemplate<String, ChatMessage> redisTemplate;

    private final String CHAT_KEY = "poopalchat";

    //expires in 15 mins
    public void sendMessage(ChatMessage message) {
        String key = CHAT_KEY + ":" + UUID.randomUUID();
        redisTemplate.opsForValue().set(key, message, Duration.ofMinutes(15));
    }

    public List<ChatMessage> getMessages() {
        Set<String> keys = redisTemplate.keys(CHAT_KEY + ":*");
        if (keys == null)
            return new ArrayList<>();

        return keys.stream()
                .map(key -> redisTemplate.opsForValue().get(key))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                .collect(Collectors.toList());
    }
}
