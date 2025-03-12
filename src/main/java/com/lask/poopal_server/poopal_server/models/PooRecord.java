package com.lask.poopal_server.poopal_server.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class PooRecord {
    private int id;
    private String pooWhere;
    private String pooType;
    private String pooColor;
    private String painBefore;
    private String painDuring;
    private String painAfter;
    private String urgent;
    private String laxative;
    private String bleeding;
    private String notes;
    private LocalDateTime timestamp;
    private String satisfactionLevel;

    public String getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public void setSatisfactionLevel(String satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPooWhere() {
        return pooWhere;
    }

    public void setPooWhere(String pooWhere) {
        this.pooWhere = pooWhere;
    }

    public String getPooType() {
        return pooType;
    }

    public void setPooType(String pooType) {
        this.pooType = pooType;
    }

    public String getPooColor() {
        return pooColor;
    }

    public void setPooColor(String pooColor) {
        this.pooColor = pooColor;
    }

    public String getPainBefore() {
        return painBefore;
    }

    public void setPainBefore(String painBefore) {
        this.painBefore = painBefore;
    }

    public String getPainDuring() {
        return painDuring;
    }

    public void setPainDuring(String painDuring) {
        this.painDuring = painDuring;
    }

    public String getPainAfter() {
        return painAfter;
    }

    public void setPainAfter(String painAfter) {
        this.painAfter = painAfter;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getLaxative() {
        return laxative;
    }

    public void setLaxative(String laxative) {
        this.laxative = laxative;
    }

    public String getBleeding() {
        return bleeding;
    }

    public void setBleeding(String bleeding) {
        this.bleeding = bleeding;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public PooRecord(int id, String pooWhere, String pooType, String pooColor, String painBefore, String painDuring,
            String painAfter, String urgent, String laxative, String bleeding, String notes, LocalDateTime timestamp,
            String satisfactionLevel) {
        this.id = id;
        this.pooWhere = pooWhere;
        this.pooType = pooType;
        this.pooColor = pooColor;
        this.painBefore = painBefore;
        this.painDuring = painDuring;
        this.painAfter = painAfter;
        this.urgent = urgent;
        this.laxative = laxative;
        this.bleeding = bleeding;
        this.notes = notes;
        this.timestamp = timestamp;
        this.satisfactionLevel = satisfactionLevel;
    }

    public PooRecord(String pooWhere, String pooType, String pooColor, String painBefore, String painDuring,
            String painAfter, String urgent, String laxative, String bleeding, String notes, String satisfactionLevel) {
        this.pooWhere = pooWhere;
        this.pooType = pooType;
        this.pooColor = pooColor;
        this.painBefore = painBefore;
        this.painDuring = painDuring;
        this.painAfter = painAfter;
        this.urgent = urgent;
        this.laxative = laxative;
        this.bleeding = bleeding;
        this.notes = notes;
        this.satisfactionLevel = satisfactionLevel;
    }

    public PooRecord() {
    }

    // method to convert to jsonp jarkata easy
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("pooWhere", pooWhere)
                .add("pooType", pooType)
                .add("pooColor", pooColor)
                .add("painBefore", painBefore)
                .add("painDuring", painDuring)
                .add("painAfter", painAfter)
                .add("urgent", urgent)
                .add("laxative", laxative)
                .add("bleeding", bleeding)
                .add("notes", notes)
                .add("timestamp",
                        timestamp != null ? timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                : "null")
                .add("satisfactionLevel", satisfactionLevel)
                .build();
    }

}
