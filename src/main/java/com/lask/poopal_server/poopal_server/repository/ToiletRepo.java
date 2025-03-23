package com.lask.poopal_server.poopal_server.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lask.poopal_server.poopal_server.models.NearestToilet;
import com.lask.poopal_server.poopal_server.models.Toilet;

@Repository
public class ToiletRepo {
    @Autowired private JdbcTemplate template;

    public void saveToilets(List<Toilet> toilets, int batchSize) {

        //prepare insert ddml statement
        final String SQL_INSERT = "INSERT INTO toilets (type, name, district, rating) VALUES (?, ?, ?, ?)";
    
        //insert function into mysql
        for (int i = 0; i < toilets.size(); i += batchSize){
            int stopAt = Math.min(i + batchSize, toilets.size());
            
            List<Toilet> batchGroup = toilets.subList(i, stopAt);

            List<Object[]> batchArgs = new ArrayList<>();
            
            for (Toilet t : batchGroup){
                Object[] args = {t.getType(), t.getName(), t.getDistrict(), t.getRating()};
                batchArgs.add(args);
            }

            template.batchUpdate(SQL_INSERT, batchArgs);
            System.out.println("Batch Inserted: " + i + " to " + stopAt);
        }
    }

    //count number of records first for checking if got any new entries when srapped. 
    //if scrap list > db records, add the new records. 
    public int countToilets() {
        String SQL_COUNT = "SELECT COUNT(*) FROM toilets";
        return template.queryForObject(SQL_COUNT, Integer.class);
    }

    //get teh list of all the toilets
    public List<Toilet> getAllToilets() {
        final String SQL_SELECT = "SELECT * FROM toilets";

        SqlRowSet results = template.queryForRowSet(SQL_SELECT);

        if (results.next()) {
            List<Toilet> toilets = new ArrayList<>();
            do {
                Toilet t = new Toilet();
                t.setId(results.getString("id"));
                t.setType(results.getString("type"));
                t.setName(results.getString("name"));
                t.setDistrict(results.getString("district"));
                t.setRating(results.getInt("rating"));
                toilets.add(t);
            } while (results.next());
            return toilets;
        } else {
            return null;
        }
    }

    public List<Toilet> getToilets() {
        final String SQL_SELECT = "SELECT * FROM toilets";

        SqlRowSet results = template.queryForRowSet(SQL_SELECT);

        if (results.next()) {
            List<Toilet> toilets = new ArrayList<>();
            do {
                Toilet t = new Toilet();
                t.setId(results.getString("id"));
                t.setType(results.getString("type"));
                t.setName(results.getString("name"));
                t.setDistrict(results.getString("district"));
                t.setRating(results.getInt("rating"));
                toilets.add(t);
            } while (results.next());
            return toilets;
        } else {
            return null;
        }
    }

    public List<NearestToilet> getNearestToilets(Double lat, Double lon) {
        //CALL SQL DB, 
        //SQL CALCULATE LATLON NEAREST TO GIVEN INPUT RETURN TOP 5
        final String SQL_SELECT_NEAREST = "SELECT *, (6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(latitude)))) AS distance_km "
                                    + "FROM toilets t LEFT JOIN placeIds p ON t.id = p.toiletId ORDER BY distance_km LIMIT 5;";

        SqlRowSet results = template.queryForRowSet(SQL_SELECT_NEAREST, lat, lon, lat);

        if (results.next()) {
            List<NearestToilet> toilets = new ArrayList<>();
            do {
                NearestToilet t = new NearestToilet();
                t.setId(results.getString("id"));
                t.setType(results.getString("type"));
                t.setName(results.getString("name"));
                t.setDistrict(results.getString("district"));
                t.setRating(results.getInt("rating"));
                t.setDistance(Double.parseDouble(results.getString("distance_km")));
                toilets.add(t);
            } while (results.next());
            return toilets;
        } else {
            return null;
        }
    }

    public void cleanData(List<String[]> listArray) {
        final String SQL_CLEAN_DATA = "UPDATE toilets SET name = ? WHERE id = ?";

        for (String[] entry : listArray) {
            
            String id = entry[0];
            String newName = entry[1];

            template.update(SQL_CLEAN_DATA, id, newName);
        }

        System.out.println("Data cleaned and updated successfully.");
    }

}
