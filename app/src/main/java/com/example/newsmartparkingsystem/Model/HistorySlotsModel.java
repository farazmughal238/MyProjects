package com.example.newsmartparkingsystem.Model;

public class HistorySlotsModel {
    private String status,pNumber,title;

    public HistorySlotsModel(String status, String pNumber, String title) {
        this.status = status;
        this.pNumber = pNumber;
        this.title = title;
    }

    public HistorySlotsModel(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
