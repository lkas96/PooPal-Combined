package com.lask.poopal_server.poopal_server.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lask.poopal_server.poopal_server.models.Toilet;
import com.lask.poopal_server.poopal_server.repository.ToiletRepo;

@Service
public class ToiletService {

    @Autowired private ToiletRepo tr;

    public List<Toilet> getAllToilets() {
        return tr.getAllToilets();
    }

}
