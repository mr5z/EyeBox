package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.nkraft.eyebox.utils.Formatter;

@Entity(tableName = "Visits")
public class Visit {

    @PrimaryKey
    @SerializedName("id")
    private long id;
    @SerializedName("date")
    private long date;
    @SerializedName("agent")
    private long agent;
    @SerializedName("client")
    private long customerId;
    @SerializedName("code")
    private String code;
    @SerializedName("status")
    private String status;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longtitude")
    private double longtitude;
    @SerializedName("signature")
    private String signature;
    @SerializedName("filesizex")
    private int fileSize;
    @SerializedName("filetypex")
    private String fileType;
    @SerializedName("filewidth")
    private int fileWidth;
    @SerializedName("fileheight")
    private int fileHeight;
    @SerializedName("filenamex")
    private String fileName;
    @SerializedName("timex")
    private long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public String getFormattedDate() {
        return Formatter.date(date);
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getAgent() {
        return agent;
    }

    public void setAgent(long agent) {
        this.agent = agent;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileWidth() {
        return fileWidth;
    }

    public void setFileWidth(int fileWidth) {
        this.fileWidth = fileWidth;
    }

    public int getFileHeight() {
        return fileHeight;
    }

    public void setFileHeight(int fileHeight) {
        this.fileHeight = fileHeight;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
