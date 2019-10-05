package com.example.motivational.recyclerviewcards;

public class Examplequestion {
    private String musername;
    private String mquestion;
    private String mdocumentid,mcurrenttime,mimageuri;

    public Examplequestion(String musername, String mquestion,String mdocumentid,String mcurrenttime,String mimageuri) {
        this.musername = musername;
        this.mquestion = mquestion;
        this.mdocumentid=mdocumentid;
        this.mcurrenttime=mcurrenttime;
        this.mimageuri=mimageuri;
    }

    public String getMusername() {
        return musername;
    }

    public String getMquestion() {
        return mquestion;
    }

    public String getMdocumentid() {
        return mdocumentid;
    }

    public String getMcurrenttime() {
        return mcurrenttime;
    }

    public String getMimageuri() {
        return mimageuri;
    }
}
