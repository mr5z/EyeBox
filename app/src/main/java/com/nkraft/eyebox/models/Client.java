package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.nkraft.eyebox.utils.Formatter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Clients")
public class Client implements Serializable {

    @PrimaryKey
    private long id;
    private String name;
    private String address;
    private String contactPerson;
    private String contactNo;
    private String emailX;
    private double creditLimit;
    private int terms;
    private long dateX; // 01/02/2008 12:24:24 pm
    private int delAll;
    private String userLogs;
    private int branchLink;
    private String salesDistrict;
    private String salesDivision;
    private String districtManager;
    private String salesManager;
    private String salesAgent;
    private double totalCredit;
    private String tinX;
    private String totalSales;
    private String adjustmentId;
    private String addOn;
    private String agent;
    private String checkedBy;
    private String deliveredBy;
    private String salesType;
    private String salesAccount;
    private String blocked;
    private String password;
    private String username;
    private String dueRestrict;
    private String creditRestrict;
    private String dateClosed;
    private String status; //":"ACTIVE",
    private String remarks;
    private String creditExt;
    private String mobileNo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailX() {
        return emailX;
    }

    public void setEmailX(String emailX) {
        this.emailX = emailX;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public long getDateX() {
        return dateX;
    }

    public void setDateX(long dateX) {
        this.dateX = dateX;
    }

    public int getDelAll() {
        return delAll;
    }

    public void setDelAll(int delAll) {
        this.delAll = delAll;
    }

    public String getUserLogs() {
        return userLogs;
    }

    public void setUserLogs(String userLogs) {
        this.userLogs = userLogs;
    }

    public int getBranchLink() {
        return branchLink;
    }

    public void setBranchLink(int branchLink) {
        this.branchLink = branchLink;
    }

    public String getSalesDistrict() {
        return salesDistrict;
    }

    public void setSalesDistrict(String salesDistrict) {
        this.salesDistrict = salesDistrict;
    }

    public String getSalesDivision() {
        return salesDivision;
    }

    public void setSalesDivision(String salesDivision) {
        this.salesDivision = salesDivision;
    }

    public String getDistrictManager() {
        return districtManager;
    }

    public void setDistrictManager(String districtManager) {
        this.districtManager = districtManager;
    }

    public String getSalesManager() {
        return salesManager;
    }

    public void setSalesManager(String salesManager) {
        this.salesManager = salesManager;
    }

    public String getSalesAgent() {
        return salesAgent;
    }

    public void setSalesAgent(String salesAgent) {
        this.salesAgent = salesAgent;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTinX() {
        return tinX;
    }

    public void setTinX(String tinX) {
        this.tinX = tinX;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public String getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(String adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public String getAddOn() {
        return addOn;
    }

    public void setAddOn(String addOn) {
        this.addOn = addOn;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(String salesAccount) {
        this.salesAccount = salesAccount;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDueRestrict() {
        return dueRestrict;
    }

    public void setDueRestrict(String dueRestrict) {
        this.dueRestrict = dueRestrict;
    }

    public String getCreditRestrict() {
        return creditRestrict;
    }

    public void setCreditRestrict(String creditRestrict) {
        this.creditRestrict = creditRestrict;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreditExt() {
        return creditExt;
    }

    public void setCreditExt(String creditExt) {
        this.creditExt = creditExt;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setDateX(String dateStr) {
        // 01/02/2008 12:24:24 pm
    }
}
