package com.lask.poopal_server.poopal_server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CORSConfig implements WebMvcConfigurer {
    @Value("${allowedOrigin}") //add in variaables on hosting platform

    private String frontend;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins(frontend)  // Allow frontend domain
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");  // Include OPTIONS
    }
    
}
