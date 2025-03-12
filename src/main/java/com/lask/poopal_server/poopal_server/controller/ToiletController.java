package com.lask.poopal_server.poopal_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lask.poopal_server.poopal_server.models.Toilet;
import com.lask.poopal_server.poopal_server.services.ToiletService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonStructure;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/toilet")
public class ToiletController {

    @Autowired ToiletService ts;

    //get list of toilets from database
    @GetMapping("/browse/all")
    public ResponseEntity<String> getAllToilets() {
        List<Toilet> toilets = ts.getAllToilets();

        //serialise to jsonp format thingy rip use the method in the model class easier nearter
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Toilet toilet : toilets) {
            jab.add(toilet.toJson());
        }

        JsonStructure js = jab.build();
        System.out.println(js);
        return ResponseEntity.ok(js.toString());
    }

    
}