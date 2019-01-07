package com.example.user.wowrecycle;

public class News {
    int newsPicture;
    String newsDesc;
    String newsUrl;


    public News() {
    }

    public News(int newsPicture, String newsDesc, String newsUrl) {
        this.newsPicture = newsPicture;
        this.newsDesc = newsDesc;
        this.newsUrl = newsUrl;
    }

    public int getNewsPicture() {
        return newsPicture;
    }

    public void setNewsPicture(int newsPicture) {
        this.newsPicture = newsPicture;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}