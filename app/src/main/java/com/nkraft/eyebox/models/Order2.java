package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Orders2")
public class Order2 implements IModel {

    @PrimaryKey
    @SerializedName("orderid")
    private long id;
    @SerializedName("quantity")
    private double quantity;
    @SerializedName("preparedqty")
    private double preparedQuantity;
    @SerializedName("productclass")
    private String productClass;
    @SerializedName("productid")
    private String productId;
    @SerializedName("soh")
    private double soh;
    @SerializedName("lotno")
    private String lotNumber;
    @SerializedName("expiry")
    private long expiry;
    @SerializedName("mfgdate")
    private long manufacturingDate;
    @SerializedName("price")
    private double price;
    @SerializedName("shipid")
    private long shipId;
    @SerializedName("status")
    private String status;
    @SerializedName("date")
    private long date;
    @SerializedName("deliverydate")
    private long deliveryDate;
    @SerializedName("orderfrom")
    private long orderFrom;
    @SerializedName("orderto")
    private long orderTo;
    @SerializedName("customer")
    private long customer;
    @SerializedName("total")
    private double total;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("anybrand")
    private String anybrand;
    @SerializedName("totalstocks")
    private double totalstocks;
    @SerializedName("salesid")
    private long salesId;
    @SerializedName("units")
    private String units;
    @SerializedName("referencecode")
    private String referenceCode;
    @SerializedName("employeesid")
    private long employeesId;
    @SerializedName("priority")
    private String priority;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPreparedQuantity() {
        return preparedQuantity;
    }

    public void setPreparedQuantity(double preparedQuantity) {
        this.preparedQuantity = preparedQuantity;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getSoh() {
        return soh;
    }

    public void setSoh(double soh) {
        this.soh = soh;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public long getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(long manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getShipId() {
        return shipId;
    }

    public void setShipId(long shipId) {
        this.shipId = shipId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public long getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(long orderFrom) {
        this.orderFrom = orderFrom;
    }

    public long getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(long orderTo) {
        this.orderTo = orderTo;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAnybrand() {
        return anybrand;
    }

    public void setAnybrand(String anybrand) {
        this.anybrand = anybrand;
    }

    public double getTotalstocks() {
        return totalstocks;
    }

    public void setTotalstocks(double totalstocks) {
        this.totalstocks = totalstocks;
    }

    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public long getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(long employeesId) {
        this.employeesId = employeesId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
