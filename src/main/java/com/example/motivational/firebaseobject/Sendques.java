package com.example.motivational.firebaseobject;

import com.google.firebase.firestore.Exclude;

public class Sendques {
    private String documentId;
    private String user;
    private String ques,currentdate,imageuri;

    public Sendques() {
    }

    public Sendques(String user, String ques,String currentdate,String imageuri){
        this.user = user;
        this.ques = ques;
        this.currentdate=currentdate;
        this.imageuri=imageuri;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public String getImageuri() {
        return imageuri;
    }

    public String getUser() {
        return user;
    }

    public String getQues() {
        return ques;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
