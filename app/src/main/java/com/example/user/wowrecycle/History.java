package com.example.user.wowrecycle;

public class History{

    private String location;
    private String date;
    private String weight;
    private String remarks;
    private int photo;


    public History(){

    }

    public History(String location, String date, String weight, String remarks, int photo) {
        this.location = location;
        this.date = date;
        this.weight = weight;
        this.remarks = remarks;
        this.photo = photo;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
