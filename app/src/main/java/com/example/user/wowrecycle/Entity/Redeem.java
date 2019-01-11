package com.example.user.wowrecycle.Entity;



public class Redeem {


    private String username;

    private int rewardIndex;

    private String date;
    private String image;
    private int point;

    public Redeem() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Redeem(String date, String username, int rewardIndex, String image, int point ){
        this.date = date;
        this.username = username;
        this.image=image;
        this.point=point;
        this.rewardIndex = rewardIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRewardIndex() {
        return rewardIndex;
    }

    public void setRewardIndex(int rewardIndex) {
        this.rewardIndex = rewardIndex;
    }
}
