package com.example.user.wowrecycle;

public class History{

    private String location;
    private String date;
    private int weight;
    private String time;
    private String remarks;
    private String photo;
    private String type;
    private String button;
    private Boolean done;
    private String uname;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }



    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public History(){

    }

    public History(String uname,String location, String date, String time,int weight, String remarks, String photo, String type, String button,Boolean done) {
        this.location = location;
        this.date = date;
        this.weight = weight;
        this.remarks = remarks;
        this.photo = photo;
        this.type = type;
        this.button = button;
        this.done=done;
        this.time=time;
        this.uname=uname;
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

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }


}
