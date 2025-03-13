package com.lask.poopal_server.poopal_server.models;

public class Place {
    private String toiletId;
    private String placeId;

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

    public Place(String toiletId, String placeId) {
        this.toiletId = toiletId;
        this.placeId = placeId;
    }

    public Place() {

    }
}
