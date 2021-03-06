package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.nkraft.eyebox.utils.Formatter;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "Payments")
public class Payment implements Parcelable, IModel {

    @PrimaryKey
    @SerializedName("idcode")
    private long id;
    @SerializedName("datex")
    private long dateX;
    @SerializedName("bankname")
    private long bankName;
    @SerializedName("checkdate")
    private long checkDate;
    @SerializedName("checkno")
    private String checkNo;
    @SerializedName("amount")
    private double amount;
    @SerializedName("receivedby")
    private long receivedBy;
    @SerializedName("delall")
    private String delAll;
    @SerializedName("userlogs")
    private String userLogs;
    @SerializedName("salesid")
    private long salesId;
    @SerializedName("terms")
    private int terms;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("checkname")
    private String checkName;
    @SerializedName("branchno")
    private int branchNo;
    @SerializedName("datedeposited")
    private long dateDeposited;
    @SerializedName("astatus")
    private String status;
    @SerializedName("customerid")
    private long customerId;
    @SerializedName("drno")
    private String drNo;
    @SerializedName("prno")
    private String productNumber;
    @SerializedName("salesdate")
    private long salesDate;
    @SerializedName("orno")
    private String orNo;
    @SerializedName("duedate")
    private long dueDate;
    @SerializedName("checkdays")
    private long checkDays;
    @SerializedName("controlno")
    private String controlNo;
    @SerializedName("deposited")
    private String deposited;
    @SerializedName("debit")
    private double debit;
    @SerializedName("validatedby")
    private long validatedBy;
    @SerializedName("receiptlayout")
    private int receiptLayout;
    @Ignore
    private List<Sale> sales;

    @Ignore
    private String bankNameStr;
    @Ignore
    private String termsName;
    @Ignore
    private boolean isChecked;

    private String receiverName;

    private String customerName;

    private int soNumber;

    public Payment() {

    }

    protected Payment(Parcel in) {
        id = in.readLong();
        dateX = in.readLong();
        bankName = in.readLong();
        checkDate = in.readLong();
        checkNo = in.readString();
        amount = in.readDouble();
        receivedBy = in.readLong();
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
        productNumber = in.readString();
        salesDate = in.readLong();
        orNo = in.readString();
        dueDate = in.readLong();
        checkDays = in.readLong();
        controlNo = in.readString();
        deposited = in.readString();
        debit = in.readDouble();
        validatedBy = in.readLong();
        receiptLayout = in.readInt();
        bankNameStr = in.readString();
        termsName = in.readString();
        isChecked = in.readByte() != 0;
        receiverName = in.readString();
        customerName = in.readString();
        soNumber = in.readInt();
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

    public long getDateX() {
        return dateX;
    }

    public void setDateX(long dateX) {
        this.dateX = dateX;
    }

    public long getBankName() {
        return bankName;
    }

    public void setBankName(long bankName) {
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

    public long getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(long receivedBy) {
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

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
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

    public long getCheckDays() {
        return checkDays;
    }

    public void setCheckDays(long checkDays) {
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
        return Formatter.currency(amount);
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public String getBankNameStr() {
        return bankNameStr;
    }

    public void setBankNameStr(String bankNameStr) {
        this.bankNameStr = bankNameStr;
    }

    public String getTermsName() {
        return termsName;
    }

    public void setTermsName(String termsName) {
        this.termsName = termsName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isSafeToDelete() {
        return status != null && (status.toLowerCase().equals("ok") || status.toLowerCase().equals("unsubmitted"));
    }

    public boolean isStatusOk() {
        return status != null && status.toLowerCase().equals("ok");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getSoNumber() {
        return soNumber;
    }

    public void setSoNumber(int soNumber) {
        this.soNumber = soNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(dateX);
        dest.writeLong(bankName);
        dest.writeLong(checkDate);
        dest.writeString(checkNo);
        dest.writeDouble(amount);
        dest.writeLong(receivedBy);
        dest.writeString(delAll);
        dest.writeString(userLogs);
        dest.writeLong(salesId);
        dest.writeInt(terms);
        dest.writeString(remarks);
        dest.writeString(checkName);
        dest.writeInt(branchNo);
        dest.writeLong(dateDeposited);
        dest.writeString(status);
        dest.writeLong(customerId);
        dest.writeString(drNo);
        dest.writeString(productNumber);
        dest.writeLong(salesDate);
        dest.writeString(orNo);
        dest.writeLong(dueDate);
        dest.writeLong(checkDays);
        dest.writeString(controlNo);
        dest.writeString(deposited);
        dest.writeDouble(debit);
        dest.writeLong(validatedBy);
        dest.writeInt(receiptLayout);
        dest.writeString(bankNameStr);
        dest.writeString(termsName);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeString(receiverName);
        dest.writeString(customerName);
        dest.writeInt(soNumber);
    }
}
