package com.example.newsmartparkingsystem.Model;

public class ParkingSlotsModel {

    private String status,pNumber;

    public ParkingSlotsModel(String status, String pNumber) {
        this.status = status;
        this.pNumber = pNumber;
    }

    public ParkingSlotsModel(){
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }
}
