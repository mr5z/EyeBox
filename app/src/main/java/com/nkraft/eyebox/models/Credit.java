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
    private String dateX;
    @SerializedName("salesid")
    private String salesid;
    @SerializedName("customerid")
    private String customerId;
    @SerializedName("prno")
    private String prNo;
    @SerializedName("payid")
    private String payId;
    @SerializedName("payamount")
    private String payAmount;
    @SerializedName("totalpayable")
    private String totalPayable;
}
