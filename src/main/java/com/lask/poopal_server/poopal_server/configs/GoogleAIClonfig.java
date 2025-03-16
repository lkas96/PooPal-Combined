package com.lask.poopal_server.poopal_server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.genai.Client;

@Configuration
public class GoogleAIClonfig {

    @Value("${google.ai.apikey}")
    private String apikey;

    @Bean
    public Client googleAIClient() {
        return Client.builder().apiKey(apikey).build();
    }

    
}
