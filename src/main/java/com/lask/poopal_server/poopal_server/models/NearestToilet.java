package com.lask.poopal_server.poopal_server.models;

import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class NearestToilet {
    private String id;
    private String name;
    private String district;
    private String type;
    private int rating;
    private Double distance;

    //to json format helper method easier
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("district", district)
            .add("type", type)
            .add("rating", rating)
            .add("distance", distance)
            .build();
    }
    
    public NearestToilet(String id, String name, String district, String type, int rating, Double distance) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.type = type;
        this.rating = rating;
        this.distance = distance;
    }

    public NearestToilet(String name, String district, String type, int rating, Double distance) {
        this.id = UUID.randomUUID().toString().substring(0,6);
        this.name = name;
        this.district = district;
        this.type = type;
        this.rating = rating;
        this.distance = distance;
    }

    public NearestToilet(){
        this.id = UUID.randomUUID().toString().substring(0,6);
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    

}
