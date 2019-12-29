package com.example.communication.model;

public class Words {

    int wID;
    String Eng;
    String Myn;
    String category;

    public Words(int wID, String eng, String myn, String category) {
        this.wID = wID;
        Eng = eng;
        Myn = myn;
        this.category = category;
    }

    public int getwID() {
        return wID;
    }

    public void setwID(int wID) {
        this.wID = wID;
    }

    public String getEng() {
        return Eng;
    }

    public void setEng(String eng) {
        Eng = eng;
    }

    public String getMyn() {
        return Myn;
    }

    public void setMyn(String myn) {
        Myn = myn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
