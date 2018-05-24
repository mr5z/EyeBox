package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Credits")
public class Credit {
    @PrimaryKey
    @SerializedName("id")
    private long id;
    @SerializedName("datex")
    private long dateX;
    @SerializedName("salesid")
    private long salesId;
    @SerializedName("customerid")
    private long customerId;
    @SerializedName("prno")
    private long prNo;
    @SerializedName("payid")
    private long payId;
    @SerializedName("payamount")
    private double payAmount;
    @SerializedName("totalpayable")
    private double totalPayable;
    @SerializedName("excess")
    private double excess;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDateX() {
        return dateX;
    }

    public void setDateX(long dateX) {
        this.dateX = dateX;
    }

    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getPrNo() {
        return prNo;
    }

    public void setPrNo(long prNo) {
        this.prNo = prNo;
    }

    public long getPayId() {
        return payId;
    }

    public void setPayId(long payId) {
        this.payId = payId;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public double getExcess() {
        return excess;
    }

    public void setExcess(double excess) {
        this.excess = excess;
    }
}
