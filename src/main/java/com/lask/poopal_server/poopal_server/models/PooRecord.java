package com.lask.poopal_server.poopal_server.models;

import java.time.LocalDateTime;

public class PooRecord {
    private int id;
    private String pooWhere;
    public String getPooWhere() {
        return pooWhere;
    }

    public void setPooWhere(String pooWhere) {
        this.pooWhere = pooWhere;
    }

    private String pooType;
    private String pooColor;
    private int painBefore;
    private int painDuring;
    private int painAfter;
    private boolean urgent;
    private boolean laxative;
    private boolean bleeding;
    private String notes;
    private LocalDateTime timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPainBefore() {
        return painBefore;
    }

    public void setPainBefore(int painBefore) {
        this.painBefore = painBefore;
    }

    public int getPainDuring() {
        return painDuring;
    }

    public void setPainDuring(int painDuring) {
        this.painDuring = painDuring;
    }

    public int getPainAfter() {
        return painAfter;
    }

    public void setPainAfter(int painAfter) {
        this.painAfter = painAfter;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public boolean isLaxative() {
        return laxative;
    }

    public void setLaxative(boolean laxative) {
        this.laxative = laxative;
    }

    public boolean isBleeding() {
        return bleeding;
    }

    public void setBleeding(boolean bleeding) {
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

    public PooRecord(int id, String pooWhere, String pooType, String pooColor, int painBefore, int painDuring,
            int painAfter, boolean urgent, boolean laxative, boolean bleeding, String notes, LocalDateTime timestamp) {
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
    }

    public PooRecord(String pooWhere, String pooType, String pooColor, int painBefore, int painDuring, int painAfter,
            boolean urgent, boolean laxative, boolean bleeding, String notes) {
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
    }

    public PooRecord() {
    }

}
