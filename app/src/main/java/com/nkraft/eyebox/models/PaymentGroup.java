package com.nkraft.eyebox.models;

public class PaymentGroup {

    private long customerId;
    private String clientName;
    private String productNumber;
    private long payDate;
    private double totalPayment;
    private String status;

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

    public long getPayDate() {
        return payDate;
    }

    public void setPayDate(long payDate) {
        this.payDate = payDate;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
