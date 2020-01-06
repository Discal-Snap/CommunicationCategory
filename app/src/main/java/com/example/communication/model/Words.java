package com.example.communication.model;

public class Words {

    int wID;
    String Eng;
    String Myn;
    String category;
    String file_name;
    String file_location;

    public Words(int wID, String eng, String myn, String category) {
        this.wID = wID;
        Eng = eng;
        Myn = myn;
        this.category = category;
    }

    public Words(int wID, String eng, String myn, String category, String file_name, String file_location) {
        this.wID = wID;
        Eng = eng;
        Myn = myn;
        this.category = category;
        this.file_name = file_name;
        this.file_location = file_location;
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_location() {
        return file_location;
    }

    public void setFile_location(String file_location) {
        this.file_location = file_location;
    }

}
