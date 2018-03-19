package com.nkraft.eyebox.models;

public class Product {

    private String name;

    private String subName;

    private float price;

    private int soh;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSoh() {
        return soh;
    }

    public void setSoh(int soh) {
        this.soh = soh;
    }
}
