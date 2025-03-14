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
        JsonObject jo = Json.createObjectBuilder().add("topPooColor", topPooColor).build();
        System.out.println("Top Poo Color : " + jo.toString());
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
    
    @GetMapping("/summary/latestPoo")
    public ResponseEntity<String> getLastestPoo (@RequestHeader("userId") String userId) {

        PooRecord lastestPoo = ps.getLastestPoo(userId);

        JsonArrayBuilder jab = Json.createArrayBuilder();
        jab.add(lastestPoo.toJson());

        JsonStructure js = jab.build();

        System.out.println(js);

        return ResponseEntity.ok(js.toString());
    }
    
    


    //FOR NOW DO ONE BY ONE
    //MAYBE NEXT TIME IF GOT TIME AND BRAIN CELLS
    //COMBINE ALL THESE DETAILS INTO ONE BIG JSON CALL
    //HAHA BEETCH SMART.
    //ALSO MOVE OUT TO THE DIFFERENT CONTROLLERS/SERVICES INSTEAD

    //FOR TRRENDS PAGES
    // 1. POOWHERE COUNTS
    // 2. POOTYPE COUNTS
    // 3. POOCOLOR COUNTS
    // 4. PAIN LEVEL COUNTS -> BEFORE, DURING, AFTER
    // 5. URGENT POO COUNTS
    // 6. LAXATIVE POO COUNTS
    // 7. BLEEDING POO COUNTS
    // 8. SATISFACTION LEVEL COUNTS

    @GetMapping("/trends/pooWhere")
    public ResponseEntity<Map<String, Integer>> getPooWhereCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> pooWhereCounts = ps.getPooWhereCounts(userId);
        System.out.println("Poo Where Counts : " + pooWhereCounts);
        return ResponseEntity.ok(pooWhereCounts);
    }

    @GetMapping("/trends/pooType")
    public ResponseEntity<Map<String, Integer>> getPooTypeCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> pooTypeCounts = ps.getPooTypeCounts(userId);
        System.out.println("Poo Type Counts : " + pooTypeCounts);
        return ResponseEntity.ok(pooTypeCounts);
    }

    @GetMapping("/trends/pooColor")
    public ResponseEntity<Map<String, Integer>> getPooColorCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> pooColorCounts = ps.getPooColorCounts(userId);
        System.out.println("Poo Color Counts : " + pooColorCounts);
        return ResponseEntity.ok(pooColorCounts);
    }
    
    @GetMapping("/trends/painLevelBefore")
    public ResponseEntity<Map<String, Integer>> getPainLevelBeforeCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> painLevelCounts = ps.getPainLevelBeforeCounts(userId);
        System.out.println("Pain Level Counts : " + painLevelCounts);
        return ResponseEntity.ok(painLevelCounts);
    }

    @GetMapping("/trends/painLevelDuring")
    public ResponseEntity<Map<String, Integer>> getPainLevelDuringCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> painLevelCounts = ps.getPainLevelDuringCounts(userId);
        System.out.println("Pain Level Counts : " + painLevelCounts);
        return ResponseEntity.ok(painLevelCounts);
    }
    
    @GetMapping("/trends/painLevelAfter")
    public ResponseEntity<Map<String, Integer>> getPainLevelAfterCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> painLevelCounts = ps.getPainLevelAfterCounts(userId);
        System.out.println("Pain Level Counts : " + painLevelCounts);
        return ResponseEntity.ok(painLevelCounts);
    }

    @GetMapping("/trends/urgentPoo")
    public ResponseEntity<Map<String, Integer>> getUrgentPooCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> urgentPooCounts = ps.getUrgentPooCounts(userId);
        System.out.println("Urgent Poo Counts : " + urgentPooCounts);
        return ResponseEntity.ok(urgentPooCounts);
    }
    
    @GetMapping("/trends/laxativePoo")
    public ResponseEntity<Map<String, Integer>> getLaxativePooCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> laxativePooCounts = ps.getLaxativePooCounts(userId);
        System.out.println("Laxative Poo Counts : " + laxativePooCounts);
        return ResponseEntity.ok(laxativePooCounts);
    }

    @GetMapping("/trends/bleedingPoo")
    public ResponseEntity<Map<String, Integer>> getBleedingPooCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> bleedingPooCounts = ps.getBleedingPooCounts(userId);
        System.out.println("Bleeding Poo Counts : " + bleedingPooCounts);
        return ResponseEntity.ok(bleedingPooCounts);
    }

    @GetMapping("/trends/satisfactionLevel")
    public ResponseEntity<Map<String, Integer>> getSatisfactionLevelCounts(@RequestHeader("userId") String userId) {

        Map<String, Integer> satisfactionLevelCounts = ps.getSatisfactionLevelCounts(userId);
        System.out.println("Satisfaction Level Counts : " + satisfactionLevelCounts);
        return ResponseEntity.ok(satisfactionLevelCounts);
    }

    
    
}
