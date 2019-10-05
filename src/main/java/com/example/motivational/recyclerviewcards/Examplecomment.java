package com.example.motivational.recyclerviewcards;

public class Examplecomment {
    private String musername,mcomment,mtime,mimageuri;

    public Examplecomment(String musername, String mcomment,String mtime,String mimageuri) {
        this.musername = musername;
        this.mcomment = mcomment;
        this.mtime=mtime;
        this.mimageuri=mimageuri;
    }

    public String getMusername() {
        return musername;
    }

    public String getMcomment() {
        return mcomment;
    }

    public String getMtime() {
        return mtime;
    }

    public String getMimageuri() {
        return mimageuri;
    }
}
