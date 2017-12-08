package com.baidingapp.faceapp;

class AllInputRateModel {
    private String number;
    private String photoUrl;

    public AllInputRateModel(String number, String photoUrl) {
        this.number = number;
        this.photoUrl = photoUrl;
    }

    public AllInputRateModel() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
