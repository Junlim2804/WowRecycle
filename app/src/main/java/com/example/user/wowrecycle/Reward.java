package com.example.user.wowrecycle;

public class Reward {

    private int Photo;
    private String Desc;
    private String View;
    private String Detail;
    private String Tnc;


    public Reward(){

    }

    public Reward(int photo, String desc, String view, String detail, String tnc) {
        this.Photo = photo;
        this.Desc = desc;
        this.View = view;
        this.Detail = detail;
        this.Tnc = tnc;
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
