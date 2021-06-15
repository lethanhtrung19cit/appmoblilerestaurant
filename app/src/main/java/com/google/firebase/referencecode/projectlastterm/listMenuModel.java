package com.google.firebase.referencecode.projectlastterm;

public class listMenuModel {
    private String name;
    private String url;
    private double price;
    private int amount;
    private String status;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public listMenuModel(String name, String url, double price) {
        this.name = name;
        this.url = url;
        this.price = price;
    }
    public listMenuModel() {

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public listMenuModel(String name, String url, double price, int amount, String status) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.amount = amount;
        this.status = status;
    }
}

