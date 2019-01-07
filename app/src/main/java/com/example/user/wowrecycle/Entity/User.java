package com.example.user.wowrecycle.Entity;


import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Entity;

@Entity(tableName="user")
public class User {
    @PrimaryKey
    @NonNull
    private String uid;
    private String email;
    private String name;
    private String fullname;
    private String hpno;
    private String ic;
    private int bonus;
    private String address;
    private String imageString;

    public User() {
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public User(@NonNull String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
    }


    public String getHpno() {
        return hpno;
    }

    public void setHpno(String hpno) {
        this.hpno = hpno;
    }

    public User(@NonNull String uid, String email, String name, String fullname, String ic, int bonus, String address, String hpno,String imageString) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.fullname = fullname;
        this.hpno=hpno;
        this.ic = ic;
        this.bonus = bonus;
        this.address = address;
        this.imageString=imageString;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }



    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
