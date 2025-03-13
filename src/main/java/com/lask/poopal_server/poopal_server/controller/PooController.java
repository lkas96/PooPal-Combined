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
import jakarta.json.JsonObject;
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
        recordChange.setSatisfactionLevel(poo.getSatisfactionLevel());
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

    

    //FOR SUMMARY PAGES
    /// 1. COUNT NUMBER OF POOS
    /// 2. COUNT NUMBER OF POOS BY TYPE
    /// 3. COUNT NUMBER OF POOS BY COLOR
    /// 4. COUNT NUMBER OF URGENT POOS
    /// 5. COUNT NUMBER OF SATISFYING POOS
    
    @GetMapping("/summary/pooCount")
    public ResponseEntity<String> getTotalPooCounts (@RequestHeader("userId") String userId) {

        int totalPoos = ps.getTotalPooCount(userId);
        JsonObject jo = Json.createObjectBuilder().add("totalPoos", totalPoos).build();
        System.out.println("Total Poo Counts : " + jo.toString());
        return ResponseEntity.ok(jo.toString());
    }

    @GetMapping("/summary/topPooType")
    public ResponseEntity<String> getTopPooType (@RequestHeader("userId") String userId) {

        String topPooType = ps.getTopPooType(userId);
        JsonObject jo = Json.createObjectBuilder().add("topPooType", topPooType).build();
        System.out.println("Top Poo Type : " + jo.toString());
        return ResponseEntity.ok(jo.toString());
    }

    @GetMapping("/summary/topPooColor")
    public ResponseEntity<String> getTopPooColor (@RequestHeader("userId") String userId) {

        String topPooColor = ps.getTopPooColor(userId);
        JsonObject jo = Json.createObjectBuilder().add("topPooType", topPooColor).build();
        System.out.println("Top Poo Type : " + jo.toString());
        return ResponseEntity.ok(jo.toString());
    }

    @GetMapping("/summary/urgentPooCount")
    public ResponseEntity<String> getUrgentPoos (@RequestHeader("userId") String userId) {

        int[] urgentPoos = ps.getUrgentPoos(userId);
        JsonObject jo = Json.createObjectBuilder()
                        .add("totalUrgentNo", urgentPoos[0])
                        .add("totalUrgentYes", urgentPoos[1])
                        .build();
        System.out.println("Urgent Poos : " + jo.toString());
        return ResponseEntity.ok(jo.toString());
    }

    @GetMapping("/summary/satisfyingPooCount")
    public ResponseEntity<String> getSatisfyingPoos (@RequestHeader("userId") String userId) {

        int[] satisfyingPoos = ps.getSatisfyingPoos(userId);
        JsonObject jo = Json.createObjectBuilder()
                        .add("totalGood", satisfyingPoos[0])
                        .add("totalMid", satisfyingPoos[1])
                        .add("totalBad", satisfyingPoos[2])
                        .build();
                        
        System.out.println("Satisfying Poos : " + jo.toString());
        return ResponseEntity.ok(jo.toString());
    }
    
    @GetMapping("/summary/lastestPoo")
    public ResponseEntity<String> getLastestPoo (@RequestHeader("userId") String userId) {

        PooRecord lastestPoo = ps.getLastestPoo(userId);

        if (lastestPoo == null) {
            JsonObject jo = Json.createObjectBuilder().add("message", "No poo entries found, start logging your poo journey with us today!").build();
            return ResponseEntity.ok(jo.toString());
        }

        JsonStructure js = lastestPoo.toJson();
        System.out.println(js.toString());
        return ResponseEntity.ok(js.toString());
    }
    
    
    
}
