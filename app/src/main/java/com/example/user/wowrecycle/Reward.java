package com.example.user.wowrecycle;

public class Reward {

    private int Photo;
    private String Desc;
    private String View;

    public Reward(){

    }

    public Reward(int photo, String desc, String view) {
        this.Photo = photo;
        this.Desc = desc;
        this.View = view;
    }

    //getter and setter

    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getView() {
        return View;
    }

    public void setView(String view) {
        View = view;
    }



}
