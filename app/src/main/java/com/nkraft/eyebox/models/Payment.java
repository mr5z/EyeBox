package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Payments")
public class Payment implements Parcelable {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault());

    @PrimaryKey
    private long id;

    private long payDate;

    private String bankName;

    private long checkDate;

    private String checkNumber;

    private String receiver;

    private long salesId;

    private String terms;

    private int branch;

    private String productNumber;

    private String orderNumber;

    private String clientName;

    private long transactionDate;

    private double totalPayment;

    private String status;

    public Payment() {}

    protected Payment(Parcel in) {
        id = in.readLong();
        payDate = in.readLong();
        bankName = in.readString();
        checkDate = in.readLong();
        checkNumber = in.readString();
        receiver = in.readString();
        salesId = in.readLong();
        terms = in.readString();
        branch = in.readInt();
        productNumber = in.readString();
        orderNumber = in.readString();
        clientName = in.readString();
        transactionDate = in.readLong();
        totalPayment = in.readDouble();
        status = in.readString();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getFormattedTransactionDate() {
        return simpleDateFormat.format(new Date(transactionDate));
    }

    public String getFormattedCheckDate() {
        return simpleDateFormat.format(new Date(checkDate));
    }

    public String getFormattedTotalPayment() {
        return String.format(Locale.getDefault(), "%.02f", totalPayment);
    }

    public String getFormattedSalesId() {
        return String.format(Locale.getDefault(), "Sales ID: %d", salesId);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPayDate() {
        return payDate;
    }

    public String getFormattedPayDate() {
        return simpleDateFormat.format(new Date(payDate));
    }

    public void setPayDate(long payDate) {
        this.payDate = payDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(payDate);
        parcel.writeString(bankName);
        parcel.writeLong(checkDate);
        parcel.writeString(checkNumber);
        parcel.writeString(receiver);
        parcel.writeLong(salesId);
        parcel.writeString(terms);
        parcel.writeInt(branch);
        parcel.writeString(productNumber);
        parcel.writeString(orderNumber);
        parcel.writeString(clientName);
        parcel.writeLong(transactionDate);
        parcel.writeDouble(totalPayment);
        parcel.writeString(status);
    }
}
