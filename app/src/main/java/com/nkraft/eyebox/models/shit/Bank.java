package com.nkraft.eyebox.models.shit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Banks")
public class Bank {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("company")
    private long company;
    @SerializedName("accountname")
    private String accountName;
    @SerializedName("accountnumber")
    private String accountNumber;
    @SerializedName("namex")
    private String namex;
    @SerializedName("userlogs")
    private String userLogs;
    @SerializedName("delall")
    private int delall;
    @SerializedName("branchno")
    private int branchNo;
    @SerializedName("bankaddress")
    private String bankAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCompany() {
        return company;
    }

    public void setCompany(long company) {
        this.company = company;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNamex() {
        return namex;
    }

    public void setNamex(String namex) {
        this.namex = namex;
    }

    public String getUserLogs() {
        return userLogs;
    }

    public void setUserLogs(String userLogs) {
        this.userLogs = userLogs;
    }

    public int getDelall() {
        return delall;
    }

    public void setDelall(int delall) {
        this.delall = delall;
    }

    public int getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(int branchNo) {
        this.branchNo = branchNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    @Override
    public String toString() {
        return namex;
    }
}
