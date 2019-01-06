package com.example.user.wowrecycle;

public class BookDetail {
    private String name;
    private String address;
    private String image;
    private String date;
    private String remark;


    public BookDetail(String name, String address, String image, String date, String remark) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.date = date;
        this.remark = remark;
    }

    @Override
    public String toString() {
       return "BookDetail{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                "," +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
