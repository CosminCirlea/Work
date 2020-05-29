package com.example.worldapp.Models;

public class OfflineSyncModel {
    private String date;
    private String someData;

    public OfflineSyncModel(String date, String someData) {
        this.date = date;
        this.someData = someData;
    }

    public OfflineSyncModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }
}
