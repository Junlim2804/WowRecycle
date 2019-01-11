package com.example.user.wowrecycle.Entity;

public class Reward {

    private String Photo;
    private int Points;
    private String View;
    private String Detail;
    private String Tnc;


    public Reward(){

    }

    public Reward(String photo, int points, String view, String detail, String tnc) {
        this.Photo = photo;
        this.Points = points;
        this.View = view;
        this.Detail = detail;
        this.Tnc = tnc;
    }

    public Reward(String photo, int points, String detail, String tnc) {
        Photo = photo;
        Points = points;
        Detail = detail;
        Tnc = tnc;
    }

    //getter and setter

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public String getView() {
        return View;
    }

    public void setView(String view) {
        View = view;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getTnc() {
        return Tnc;
    }

    public void setTnc(String tnc) {
        Tnc = tnc;
    }
}
