package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Visits")
public class Visit {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long customerId;
    private String clientSignaturePath;
    private long dateVisit;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getClientSignaturePath() {
        return clientSignaturePath;
    }

    public void setClientSignaturePath(String clientSignaturePath) {
        this.clientSignaturePath = clientSignaturePath;
    }

    public long getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(long dateVisit) {
        this.dateVisit = dateVisit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
