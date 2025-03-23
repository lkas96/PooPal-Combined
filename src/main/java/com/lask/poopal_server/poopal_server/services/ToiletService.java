package com.lask.poopal_server.poopal_server.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lask.poopal_server.poopal_server.models.NearestToilet;
import com.lask.poopal_server.poopal_server.models.Toilet;
import com.lask.poopal_server.poopal_server.repository.ToiletRepo;

@Service
public class ToiletService {

    @Autowired private ToiletRepo tr;

    public List<Toilet> getAllToilets() {
        return tr.getAllToilets();
    }

    public List<NearestToilet> getNearestToilets(Double lat, Double lon) {
        //CALL SQL DB, 
        //SQL CALCULATE LATLON NEAREST TO GIVEN INPUT RETURN TOP 5
        return tr.getNearestToilets(lat, lon);

    }



}
