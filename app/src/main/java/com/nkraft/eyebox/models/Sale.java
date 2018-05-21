package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.nkraft.eyebox.utils.Formatter;

@Entity(tableName = "Sales")
public class Sale {

    @NonNull
    @PrimaryKey
    @SerializedName("salesidx")
    final private String id;
    @SerializedName("customerid")
    private long customerId;
    @SerializedName("companyname")
    private String companyName;
    @SerializedName("agent")
    private long agent;
    @SerializedName("checkedby")
    private String checkedBy;
    @SerializedName("deliveredby")
    private long deliveredBy;
    @SerializedName("adjustments")
    private String adjustments;
    @SerializedName("adjustmentamount")
    private String adjustmentAmount;
    @SerializedName("userlogs")
    private String userLogs;
    @SerializedName("delall")
    private String delAll;
    @SerializedName("drno")
    private String drNo;
    @SerializedName("sino")
    private String siNo;
    @SerializedName("prno")
    private String prNo;
    @SerializedName("orno")
    private String orNo;
    @SerializedName("amount")
    private double amount;
    @SerializedName("salesdate")
    private long salesDate;
    @SerializedName("totalamount")
    private double totalAmount;
    @SerializedName("salestype")
    private String salesType;
    @SerializedName("salesaccount")
    private long salesAccount;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("origtotal")
    private double origTotal;
    @SerializedName("branchno")
    private int branchNo;
    @SerializedName("debit")
    private double debit;
    @SerializedName("payamount")
    private double payAmount;
    @SerializedName("balance")
    private double balance;
    @SerializedName("syncx")
    private String syncX;
    @SerializedName("modified")
    private String modified;
    @SerializedName("datemodified")
    private long dateModified;
    @SerializedName("deliverydate")
    private long deliveryDate;
    @SerializedName("transaction")
    private double transaction;
    @SerializedName("blocked")
    private String blocked;
    @SerializedName("duedate")
    private long dueDate;
    @SerializedName("terms")
    private int terms;
    @SerializedName("printx")
    private String printX;
    @SerializedName("printlayout")
    private long printlayout;
    @SerializedName("audit")
    private String audit;
    @SerializedName("paymentmethod")
    private String paymentMethod;
    @SerializedName("datedeposit")
    private long dateDeposit;
    @SerializedName("checkdate")
    private long checkDate;
    @SerializedName("notification")
    private String notification;
    @SerializedName("itemcount")
    private double itemCount;

    @Ignore
    private boolean isChecked;

    @Ignore
    private boolean isDisabled;

    @Ignore
    private double tempAmount;

    public Sale(@NonNull String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getAgent() {
        return agent;
    }

    public void setAgent(long agent) {
        this.agent = agent;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public long getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(long deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(String adjustments) {
        this.adjustments = adjustments;
    }

    public String getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(String adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public String getUserLogs() {
        return userLogs;
    }

    public void setUserLogs(String userLogs) {
        this.userLogs = userLogs;
    }

    public String getDelAll() {
        return delAll;
    }

    public void setDelAll(String delAll) {
        this.delAll = delAll;
    }

    public String getDrNo() {
        return drNo;
    }

    public void setDrNo(String drNo) {
        this.drNo = drNo;
    }

    public String getSiNo() {
        return siNo;
    }

    public void setSiNo(String siNo) {
        this.siNo = siNo;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public String getOrNo() {
        return orNo;
    }

    public void setOrNo(String orNo) {
        this.orNo = orNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(long salesDate) {
        this.salesDate = salesDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public long getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(long salesAccount) {
        this.salesAccount = salesAccount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getOrigTotal() {
        return origTotal;
    }

    public void setOrigTotal(double origTotal) {
        this.origTotal = origTotal;
    }

    public int getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(int branchNo) {
        this.branchNo = branchNo;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSyncX() {
        return syncX;
    }

    public void setSyncX(String syncX) {
        this.syncX = syncX;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getTransaction() {
        return transaction;
    }

    public void setTransaction(double transaction) {
        this.transaction = transaction;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public String getPrintX() {
        return printX;
    }

    public void setPrintX(String printX) {
        this.printX = printX;
    }

    public long getPrintlayout() {
        return printlayout;
    }

    public void setPrintlayout(long printlayout) {
        this.printlayout = printlayout;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getDateDeposit() {
        return dateDeposit;
    }

    public void setDateDeposit(long dateDeposit) {
        this.dateDeposit = dateDeposit;
    }

    public long getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(long checkDate) {
        this.checkDate = checkDate;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public double getItemCount() {
        return itemCount;
    }

    public void setItemCount(double itemCount) {
        this.itemCount = itemCount;
    }

    public String getFormattedId() {
        return Formatter.string("Sales ID: %s", id);
    }

    public String getFormattedTransaction() {
        return String.valueOf((int)transaction);
    }

    public double getTotalNet() {
        return balance;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    public boolean equals(Object obj) {
        Sale other = (Sale) obj;
        if (other == null) return false;
        String id1 = getId();
        String id2 = other.getId();
        return id1 != null && id1.equals(id2);
    }

    public double getTempAmount() {
        return tempAmount;
    }

    public void setTempAmount(double tempAmount) {
        this.tempAmount = tempAmount;
    }
}
