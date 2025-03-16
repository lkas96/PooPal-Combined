package com.lask.poopal_server.poopal_server.controller;

import java.io.IOException;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lask.poopal_server.poopal_server.services.GoogleAIService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@CrossOrigin(origins = "http://localhost:4200") // Apply globally for now, can also put in the individual mappings thingy
@RestController
@RequestMapping("/ai")
public class GoogleAIController {
    
    @Autowired private GoogleAIService gas;
    
    //from the chat frontend, take user query call to google ai service
    @PostMapping("/chat")
    public ResponseEntity<String> sendQuery(@RequestBody String userPrompt, @RequestHeader("userId") String userId) throws IOException, HttpException {
        String resp =  gas.getResponse(userPrompt, userId);

        return ResponseEntity.ok(resp);
    }
    
}
