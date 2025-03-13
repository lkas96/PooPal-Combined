package com.lask.poopal_server.poopal_server.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lask.poopal_server.poopal_server.models.Place;

@Repository
public class PlacesRepo {
    @Autowired private JdbcTemplate template;

    //Add PlaceId Entry to PlaceIds table for single entry
    public void addPlaceId(String toiletId, String placeId){
        final String sql = "INSERT INTO PlaceIds (toiletId, placeId) VALUES (?, ?)";
        template.update(sql, toiletId, placeId);
        System.out.println("ToiletId/PlaceId: " + toiletId + " / " + placeId + "added to PlaceIds table");
    }
    
    //bulk add for initial processing
    public void addBatchPlaceIds(List<Place> places, int batchSize) {

        //prepare insert ddml statement
        final String SQL_INSERT = "INSERT INTO placeIds (toiletId, placeId) VALUES (?, ?)";
    
        //insert function into mysql
        for (int i = 0; i < places.size(); i += batchSize){
            int stopAt = Math.min(i + batchSize, places.size());
            
            List<Place> batchGroup = places.subList(i, stopAt);

            List<Object[]> batchArgs = new ArrayList<>();
            
            for (Place p : batchGroup){
                Object[] args = {p.getToiletId(), p.getPlaceId()};
                batchArgs.add(args);
            }

            template.batchUpdate(SQL_INSERT, batchArgs);
            System.out.println("Batch Inserted: " + i + " to " + stopAt);
            System.out.println("Total Places: " + places.size());
        }
    }

    public int countPlaceIds() {
        final String countPlaceIds = "SELECT COUNT(*) FROM placeIds";
        return template.queryForObject(countPlaceIds, Integer.class);
    }
    
}
