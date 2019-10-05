package com.example.motivational.firebaseobject;

public class Sendcomment {
    private String user,comment,currentdate,imageuri;

    public Sendcomment(String user, String comment,String currentdate,String imageuri) {
        this.user = user;
        this.comment = comment;
        this.currentdate=currentdate;
        this.imageuri=imageuri;
    }

    public Sendcomment() {
    }

    public String getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public String getImageuri() {
        return imageuri;
    }
}
