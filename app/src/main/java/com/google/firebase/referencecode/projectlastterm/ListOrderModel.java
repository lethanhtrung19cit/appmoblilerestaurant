package com.google.firebase.referencecode.projectlastterm;

public class ListOrderModel {
    private String nameTable;
    private String nameFood;
    private String urlimg;
    private double price;
    private int amount;
    private double sumPrice;

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public ListOrderModel(String urlimg, String nameFood, double price, int amount, double sumPrice) {

        this.nameFood = nameFood;
        this.amount = amount;
        this.sumPrice = sumPrice;
        this.urlimg = urlimg;
        this.price=price;
    }

    public ListOrderModel() {
    }

    public String getUrlImg() {
        return urlimg;
    }

    public void setUrlImg(String urlimg) {
        this.urlimg = urlimg;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
