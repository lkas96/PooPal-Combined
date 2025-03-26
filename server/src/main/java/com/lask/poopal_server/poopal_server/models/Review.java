package com.lask.poopal_server.poopal_server.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Review {
    private int reviewId;
    private int cleanliness;
    private int smell;
    private String recommended;
    private String comments;
    private String imageUrl;
    private int toiletId;
    private LocalDateTime timestamp;

    public Review(int reviewId, int cleanliness, int smell, String recommended, String comments, String imageUrl,
            int toiletId,
            LocalDateTime timestamp) {
        this.reviewId = reviewId;
        this.cleanliness = cleanliness;
        this.smell = smell;
        this.recommended = recommended;
        this.comments = comments;
        this.imageUrl = imageUrl;
        this.toiletId = toiletId;
        this.timestamp = timestamp;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Review() {
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(int cleanliness) {
        this.cleanliness = cleanliness;
    }

    public int getSmell() {
        return smell;
    }

    public void setSmell(int smell) {
        this.smell = smell;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getToiletId() {
        return toiletId;
    }

    public void setToiletId(int toiletId) {
        this.toiletId = toiletId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("reviewId", reviewId)
                .add("cleanliness", cleanliness)
                .add("smell", smell)
                .add("recommended", recommended)
                .add("comments", comments)
                .add("imageUrl", imageUrl)
                .add("toiletId", toiletId)
                .add("timestamp",
                        timestamp != null ? timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                : "null")
                .build();
    }

}
