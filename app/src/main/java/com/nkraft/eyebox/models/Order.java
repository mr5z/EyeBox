package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Orders")
public class Order implements Parcelable {

    @Ignore
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault());

    @Ignore
    private static long idCounter;

    @PrimaryKey
    private long id;
    private int quantity;
    private long dateOrdered;
    private String clientName;
    @Ignore
    private Product product;

    public Order() {

    }

    public Order(Product product, int quantity) {
        this.id = generateId();
        this.quantity = quantity;
        this.product = product;
    }

    protected Order(Parcel in) {
        id = in.readLong();
        quantity = in.readInt();
        dateOrdered = in.readLong();
        clientName = in.readString();
        product = in.readParcelable(Product.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private static long generateId() {
        return idCounter++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(quantity);
        parcel.writeLong(dateOrdered);
        parcel.writeString(clientName);
        parcel.writeParcelable(product, i);
    }

    public long getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(long dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getFormattedDateOrdered() {
        return simpleDateFormat.format(new Date(dateOrdered));
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
