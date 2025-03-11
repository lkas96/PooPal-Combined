package com.lask.poopal_server.poopal_server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.lask.poopal_server.poopal_server.models.PooRecord;
import com.lask.poopal_server.poopal_server.services.PooService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonStructure;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:4200") // Apply globally for now, can also put in the individual mappings thingy
@RestController
@RequestMapping("/poo")
public class PooController {
    @Autowired private PooService ps;

    // WORKING - COMPLETED DO NOT TOUCH
    //save a poo entry
    @PostMapping("/records/new")
    public ResponseEntity<Map<String, String>> savePooEntry(@RequestBody PooRecord poo, @RequestHeader("userId") String userId) {
        System.out.println("ADDING NEW ENTRY");
        ps.savePooEntry(userId, poo);

        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Poo entry saved successfully");
        return ResponseEntity.ok(resp);
    }
    
    // WORKING - COMPLETED DO NOT TOUCH
    //REVISED JSONP DATEIMER SHIT KEEPS MESSING UP ON FRONTEND CRI
    //get all poo entries
    @GetMapping("/records/all")
    public ResponseEntity<String> getAllPoos(@RequestHeader("userId") String userId) {
        List<PooRecord> records = ps.getAllPooEntries(userId);
        
        //serialise to jsonp format thingy rip use the method in the model class easier nearter
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (PooRecord record : records) {
            jab.add(record.toJson());
        }
        JsonStructure js = jab.build();
        System.out.println(js);

        return ResponseEntity.ok(js.toString());
    }

    //get a poo by id for editing for view indepth
    @GetMapping("/records/{id}")
    public ResponseEntity<String> getPooById(@PathVariable int id, @RequestHeader String userId) {
        
        PooRecord poo = ps.getPooEntry(userId, id);
        JsonStructure js = poo.toJson();
        System.out.println(js.toString());
        return ResponseEntity.ok(js.toString());
    }

    //ok working now
    //update a poo by id
    @PutMapping("/records/edit/{id}")
    public ResponseEntity<PooRecord> updatePooById(@PathVariable int id, @RequestBody PooRecord poo, @RequestHeader("userId") String userId) {
       
        System.out.println(poo.toString());

        PooRecord recordChange = ps.getPooEntry(userId, id);

        recordChange.setPooWhere(poo.getPooWhere());
        recordChange.setPooType(poo.getPooType());
        recordChange.setPooColor(poo.getPooColor());
        recordChange.setPainBefore(poo.getPainBefore());
        recordChange.setPainDuring(poo.getPainDuring());
        recordChange.setPainAfter(poo.getPainAfter());
        recordChange.setUrgent(poo.getUrgent());
        recordChange.setLaxative(poo.getLaxative());
        recordChange.setBleeding(poo.getBleeding());
        recordChange.setNotes(poo.getNotes());
        recordChange.setTimestamp(poo.getTimestamp());

        ps.updatePooEntry(userId, poo);

        return ResponseEntity.ok(recordChange);
    }
    
    //delete a poo by id
    @DeleteMapping("/records/delete/{id}")
    public ResponseEntity<Map<String, String>> deletePooById(@PathVariable int id, @RequestHeader("userId") String userId) {
        ps.deletePooEntry(userId, id);

        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Poo entry deleted successfully");
        return ResponseEntity.ok(resp);
    }
    
}
