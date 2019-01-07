package com.example.user.wowrecycle;

public class History{

    private String location;
    private String date;
    private int weight;
    private String remarks;
    private String photo;
    private String type;


    public History(){

    }

    public History(String location, String date, int weight, String remarks, String photo, String type) {
        this.location = location;
        this.date = date;
        this.weight = weight;
        this.remarks = remarks;
        this.photo = photo;
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
