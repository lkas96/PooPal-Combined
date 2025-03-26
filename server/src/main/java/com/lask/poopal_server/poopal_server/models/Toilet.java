package com.lask.poopal_server.poopal_server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Toilet {
    private String id;
    private String name;
    private String district;
    private String type;
    private int rating;
    private String placeId;
    private String lat;
    private String lon;

    // to json format helper method easier
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .add("district", district)
                .add("type", type)
                .add("rating", rating)
                .add("placeId", placeId)
                .add("lat", lat)
                .add("lon", lon)
                .build();
    }

    public Toilet(String id, String name, String district, String type, int rating, String placeId, String lat, String lon) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.type = type;
        this.rating = rating;
        this.placeId = placeId;
        this.lat = lat;
        this.lon = lon;
    }

    public Toilet(String id, String name, String district, String type, int rating) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.type = type;
        this.rating = rating;
    }

    public Toilet(String name, String district, String type, int rating, String placeId, String lat, String lon) {
        this.name = name;
        this.district = district;
        this.type = type;
        this.rating = rating;
        this.placeId = placeId;
        this.lat = lat;
        this.lon = lon;
    }

    public Toilet(String name, String district, String type, int rating) {
        this.name = name;
        this.district = district;
        this.type = type;
        this.rating = rating;
    }

    public Toilet() {
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

}
