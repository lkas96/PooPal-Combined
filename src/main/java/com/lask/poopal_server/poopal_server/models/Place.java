package com.lask.poopal_server.poopal_server.models;

public class Place {
    private String toiletId;
    private String placeId;
    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getToiletId() {
        return toiletId;
    }

    public void setToiletId(String toiletId) {
        this.toiletId = toiletId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Place() {

    }

    public Place(String toiletId, String placeId, Double latitude, Double longitude) {
        this.toiletId = toiletId;
        this.placeId = placeId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
