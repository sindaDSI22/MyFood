package com.example.myfood;

public class MainModel {

    String nomRes,typeRes,imageRes;


    MainModel(){

     }
    public MainModel(String imageRes ,String nomRes, String typeRes) {
        this.nomRes = nomRes;
        this.typeRes = typeRes;
        this.imageRes = imageRes;
    }

    public String getNomRes() {
        return nomRes;
    }

    public void setNomRes(String nomRes) {
        this.nomRes = nomRes;
    }

    public String getTypeRes() {
        return typeRes;
    }

    public void setTypeRes(String typeRes) {
        this.typeRes = typeRes;
    }

    public String getImageRes() {
        return imageRes;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }
}
