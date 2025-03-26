package com.lask.poopal_server.poopal_server.services;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.lask.poopal_server.poopal_server.models.PooRecord;
import com.lask.poopal_server.poopal_server.repository.PooRepo;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonStructure;

@Service
public class GoogleAIService {

    @Value("${google.ai.model}")
    private String model;

    @Value("${google.ai.instructions}")
    private String instructionSet;

    private final Client client;

    public GoogleAIService(Client client) {
        this.client = client;
    }

    @Autowired
    private PooRepo pr;

    public String getResponse(String userPrompt, String userId) throws IOException, HttpException {

        // get user records from the database
        List<PooRecord> userRecords = pr.getAllPooEntries(userId);

        // convert the records to json
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (PooRecord record : userRecords) {
            jab.add(record.toJson());
        }
        JsonStructure js = jab.build();

        //convert to string for the api call
        String userJsonRecords = js.toString();

        // make the call to the google api to get response lah
        GenerateContentResponse resp = client.models.generateContent(model, userPrompt + instructionSet + userJsonRecords, null);

        System.out.println("UserID: " + userId);
        System.out.println("Reply from google ai:");
        System.out.println(resp.text());

        return resp.text();
    }

}
