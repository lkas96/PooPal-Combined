package com.lask.poopal_server.poopal_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.lask.poopal_server.poopal_server.models.PooRecord;
import com.lask.poopal_server.poopal_server.services.PooService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:4200") // Apply globally for now, can also put in the individual mappings thingy
@RestController
@RequestMapping("/poo")
public class PooController {
    @Autowired private PooService ps;

    //save a poo entry
    @PostMapping("/records/new")
    public void savePooEntry(@RequestBody PooRecord poo, String userId) {
        ps.savePooEntry(poo);
    }

    //get all poos of a user
    @GetMapping("/records/all")
    public List<PooRecord> getAllPoos(@RequestBody String userId) {
        return ps.getAllPooEntries(userId);
    }

    //get a poo by id
    @GetMapping("/records/{id}")
    public ResponseEntity<PooRecord> getPooById(@PathVariable int id, @RequestBody String userId) {
        PooRecord poo = ps.getPooEntry(userId, id);
        if (poo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(poo);
    }

    //update a poo by id
    @GetMapping("/records/update/{id}")
    public ResponseEntity<PooRecord> updatePooById(@PathVariable int id, @RequestBody PooRecord poo, String userId) {
        PooRecord recordChange = ps.getPooEntry(userId, id);

        if (recordChange == null) {
            //something wrong, no poo records retrivede
            return ResponseEntity.notFound().build();
        }
        
        recordChange.setPublic(poo.isPublic());
        recordChange.setPooType(poo.getPooType());
        recordChange.setPooColor(poo.getPooColor());
        recordChange.setPainBefore(poo.getPainBefore());
        recordChange.setPainDuring(poo.getPainDuring());
        recordChange.setPainAfter(poo.getPainAfter());
        recordChange.setUrgent(poo.isUrgent());
        recordChange.setLaxative(poo.isLaxative());
        recordChange.setBleeding(poo.isBleeding());
        recordChange.setNotes(poo.getNotes());
        recordChange.setTimestamp(poo.getTimestamp());

        ps.updatePooEntry(userId, poo);

        return ResponseEntity.ok(recordChange);
    }
    
}
