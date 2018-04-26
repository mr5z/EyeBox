package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.nkraft.eyebox.utils.Formatter;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "Payments")
public class Payment implements Parcelable {

    @PrimaryKey
    private long id;
    private String dateX;
    private String bankName;
    private long checkDate;
    private String checkNo;
    private double amount;
    private String receivedBy;
    private String delAll;
    private String userLogs;
    private long salesId;
    private int terms;
    private String remarks;
    private String checkName;
    private int branchNo;
    private long dateDeposited;
    private String status;
    private long customerId;
    private String drNo;
    private String prNo;
    private long salesDate;
    private String orNo;
    private long dueDate;
    private String checkDays;
    private String controlNo;
    private String deposited;
    private double debit;
    private long validatedBy;
    private int receiptLayout;
    @Ignore
    private List<Sale> sales;

    public Payment() {

    }

    protected Payment(Parcel in) {
        id = in.readLong();
        dateX = in.readString();
        bankName = in.readString();
        checkDate = in.readLong();
        checkNo = in.readString();
        amount = in.readDouble();
        receivedBy = in.readString();
        delAll = in.readString();
        userLogs = in.readString();
        salesId = in.readLong();
        terms = in.readInt();
        remarks = in.readString();
        checkName = in.readString();
        branchNo = in.readInt();
        dateDeposited = in.readLong();
        status = in.readString();
        customerId = in.readLong();
        drNo = in.readString();
        prNo = in.readString();
        salesDate = in.readLong();
        orNo = in.readString();
        dueDate = in.readLong();
        checkDays = in.readString();
        controlNo = in.readString();
        deposited = in.readString();
        status = in.readString();
        debit = in.readDouble();
        validatedBy = in.readLong();
        receiptLayout = in.readInt();
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

    public String getDateX() {
        return dateX;
    }

    public void setDateX(String dateX) {
        this.dateX = dateX;
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

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getDelAll() {
        return delAll;
    }

    public void setDelAll(String delAll) {
        this.delAll = delAll;
    }

    public String getUserLogs() {
        return userLogs;
    }

    public void setUserLogs(String userLogs) {
        this.userLogs = userLogs;
    }

    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public int getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(int branchNo) {
        this.branchNo = branchNo;
    }

    public long getDateDeposited() {
        return dateDeposited;
    }

    public void setDateDeposited(long dateDeposited) {
        this.dateDeposited = dateDeposited;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getDrNo() {
        return drNo;
    }

    public void setDrNo(String drNo) {
        this.drNo = drNo;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public long getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(long salesDate) {
        this.salesDate = salesDate;
    }

    public String getOrNo() {
        return orNo;
    }

    public void setOrNo(String orNo) {
        this.orNo = orNo;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public String getCheckDays() {
        return checkDays;
    }

    public void setCheckDays(String checkDays) {
        this.checkDays = checkDays;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getDeposited() {
        return deposited;
    }

    public void setDeposited(String deposited) {
        this.deposited = deposited;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public long getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(long validatedBy) {
        this.validatedBy = validatedBy;
    }

    public int getReceiptLayout() {
        return receiptLayout;
    }

    public void setReceiptLayout(int receiptLayout) {
        this.receiptLayout = receiptLayout;
    }

    public String getFormattedAmount() {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        format.setGroupingUsed(true);
        return format.format(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(dateX);
        parcel.writeString(bankName);
        parcel.writeLong(checkDate);
        parcel.writeString(checkNo);
        parcel.writeDouble(amount);
        parcel.writeString(receivedBy);
        parcel.writeString(delAll);
        parcel.writeString(userLogs);
        parcel.writeLong(salesId);
        parcel.writeInt(terms);
        parcel.writeString(remarks);
        parcel.writeString(checkName);
        parcel.writeInt(branchNo);
        parcel.writeLong(dateDeposited);
        parcel.writeString(status);
        parcel.writeLong(customerId);
        parcel.writeString(drNo);
        parcel.writeString(prNo);
        parcel.writeLong(salesDate);
        parcel.writeString(orNo);
        parcel.writeLong(dueDate);
        parcel.writeString(checkDays);
        parcel.writeString(controlNo);
        parcel.writeString(deposited);
        parcel.writeString(status);
        parcel.writeDouble(debit);
        parcel.writeLong(validatedBy);
        parcel.writeInt(receiptLayout);
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
