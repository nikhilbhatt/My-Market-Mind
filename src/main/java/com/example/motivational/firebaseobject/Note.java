package com.example.motivational.firebaseobject;

public class Note {
    private String tittle;
    private int order;

    public Note() {}

    public Note(String tittle,int order) {
        this.tittle = tittle;
        this.order=order;
    }

    public String getTittle() {
        return tittle;
    }

    public int getOrder() {
        return order;
    }
}
