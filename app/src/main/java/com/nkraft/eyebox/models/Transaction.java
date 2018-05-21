package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Locale;

@Entity(tableName = "Transactions")
public class Transaction implements Parcelable {

    @PrimaryKey
    private long id;
    private String productNumber;
    private String clientName;
    private String clientAddress;
    private double balance;
    private double amount;
    private long checkDate;
    private String checkNumber;
    private String orderNumber;
    private int terms;
    private int bank;

    public Transaction(long id) {
        this.id = id;
    }

    protected Transaction(Parcel in) {
        id = in.readLong();
        productNumber = in.readString();
        clientName = in.readString();
        clientAddress = in.readString();
        balance = in.readDouble();
        amount = in.readDouble();
        checkDate = in.readLong();
        checkNumber = in.readString();
        orderNumber = in.readString();
        terms = in.readInt();
        bank = in.readInt();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public long getId() {
        return id;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public long getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(long checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(productNumber);
        parcel.writeString(clientName);
        parcel.writeString(clientAddress);
        parcel.writeDouble(balance);
        parcel.writeDouble(amount);
        parcel.writeLong(checkDate);
        parcel.writeString(checkNumber);
        parcel.writeString(orderNumber);
        parcel.writeInt(terms);
        parcel.writeInt(bank);
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
