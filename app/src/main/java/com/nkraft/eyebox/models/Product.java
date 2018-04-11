package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Entity(tableName = "Products")
public class Product {

    @PrimaryKey
    private long id;
    private String name;
    private String genericName;
    private String units;
    private double price;
    private int soh;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setCurrency(Currency.getInstance(new Locale("tl", "PH")));
        return formatter.format(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSoh() {
        return soh;
    }

    public void setSoh(int soh) {
        this.soh = soh;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
