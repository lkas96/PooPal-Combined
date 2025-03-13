package com.lask.poopal_server.poopal_server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lask.poopal_server.poopal_server.models.PooRecord;
import com.lask.poopal_server.poopal_server.repository.PooRepo;

@Service
public class PooService {
    @Autowired private PooRepo pr;

    //create a poo entry
    public void savePooEntry(String userId, PooRecord pooEntry) {
        pr.savePooEntry(userId, pooEntry);
    }

    //get all poo entries
    public List<PooRecord> getAllPooEntries(String userId) {
        return pr.getAllPooEntries(userId);
    }

    //get a poo entry
    public PooRecord getPooEntry(String userId, int pooId) {
        return pr.getPooEntry(userId, pooId);
    }

    //update a poo entry
    public void updatePooEntry(String userId, PooRecord pooEntry) {
        pr.updatePooEntry(userId, pooEntry);
    }

    //delete a poo entry
    public void deletePooEntry(String userId, int pooId) {
        pr.deletePooEntry(userId, pooId);
    }

    public int getTotalPooCount(String userId) {
        return pr.getTotalPooCount(userId);
    }

    public String getTopPooType(String userId) {
        return pr.getTopPooType(userId);
    }

    public String getTopPooColor(String userId) {
        return pr.getTopPooColor(userId);
    }

    public int[] getUrgentPoos(String userId) {
        return pr.getUrgentPoos(userId);
    }

    public int[] getSatisfyingPoos(String userId) {
        return pr.getSatisfyingPoos(userId);
    }

    public PooRecord getLastestPoo(String userId) {
        return pr.getLastestPoo(userId);
    }
}
