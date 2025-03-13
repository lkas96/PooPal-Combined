package com.lask.poopal_server.poopal_server.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class PlaceService {

    @Value("${google.places.apikey}") private String apiKey;

    RestTemplate rt = new RestTemplate();

    private static final String PLACES_URL = "https://places.googleapis.com/v1/places:searchText?key=";

    private static final String PLACES_URL2 = "https://places.googleapis.com/v1/places:autocomplete";
    
    //get the string and name of the place
    //called from commandlinerunner first time
    public String getPlaceId(String placeName) {
        //put the placeanem to search in textquery json raw body
        JsonObject job = Json.createObjectBuilder()
                .add("textQuery", placeName)
                .build();

        // set the custom header to add the gxgoogl whatever
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Goog-FieldMask", "places.id,places.displayName");

        //create the http object
        HttpEntity<String> entity = new HttpEntity<>(job.toString(), headers);

        //make the call dey
        ResponseEntity<String> resp = rt.exchange(
            PLACES_URL + apiKey,
            HttpMethod.POST,
            entity,
            String.class
        );

        //test okay output the table we want, 
        //now extract whatever data we want from the json
        // System.out.println(resp.getBody());

        //now extract whatever data we want from the json
        //write function here
        JsonReader jr = Json.createReader(new StringReader(resp.getBody()));
        JsonObject jo = jr.readObject();
        JsonArray places = jo.getJsonArray("places");

        if (places == null ){
            //means partial matching not fuoud, use the fallback method
            //places auto complete instead.

            JsonObject job2 = Json.createObjectBuilder()
                .add("input", placeName)
                .build();

            HttpHeaders headers2 = new HttpHeaders();
            headers2.add("X-Goog-Api-Key", apiKey);

            HttpEntity<String> entity2 = new HttpEntity<>(job2.toString(), headers2);

            ResponseEntity<String> resp2 = rt.exchange(
                PLACES_URL2,
                HttpMethod.POST,
                entity2,
                String.class
            );


            JsonReader jr2 = Json.createReader(new StringReader(resp2.getBody()));
            JsonObject jo2 = jr2.readObject();
            JsonArray suggestions = jo2.getJsonArray("suggestions");
            JsonObject suggestionsInside = suggestions.getJsonObject(0);
            JsonObject placePrediction = suggestionsInside.getJsonObject("placePrediction");
            String extracted2 = placePrediction.getString("placeId");
            
            System.out.println("Backup Method Extracted ID: " + extracted2);
            
            return extracted2;
        }

        //always get the first one if got multiple searches. first one should be the best matched example by google places api whatever
        JsonObject firstPlace = places.getJsonObject(0);
        String extractedId = firstPlace.getString("id");

        System.out.println("Extracted ID: " + extractedId);

        return extractedId;
    }

}
