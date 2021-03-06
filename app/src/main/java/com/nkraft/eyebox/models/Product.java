package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@Entity(tableName = "Products")
public class Product implements Parcelable, IModel {

    @PrimaryKey
    private long id;
    private String name;
    private String genericName;
    private String units;
    private double price;
    private int soh;
    private double byX;

    public Product() {
    }

    protected Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        genericName = in.readString();
        units = in.readString();
        price = in.readDouble();
        soh = in.readInt();
        byX = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(genericName);
        dest.writeString(units);
        dest.writeDouble(price);
        dest.writeInt(soh);
        dest.writeDouble(byX);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    public double getByX() {
        return byX;
    }

    public void setByX(double byX) {
        this.byX = byX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
