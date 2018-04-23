package com.nkraft.eyebox.models.shit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Terms")
public class Terms {

    @PrimaryKey
    private long id;
    private int days;
    private String namex;
    private int dateDelay;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getNamex() {
        return namex;
    }

    public void setNamex(String namex) {
        this.namex = namex;
    }

    public int getDateDelay() {
        return dateDelay;
    }

    public void setDateDelay(int dateDelay) {
        this.dateDelay = dateDelay;
    }

    @Override
    public String toString() {
        return namex;
    }
}
