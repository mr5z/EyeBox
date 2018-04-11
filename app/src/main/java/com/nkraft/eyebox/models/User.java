package com.nkraft.eyebox.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Users")
public class User {

    @PrimaryKey
    private long id;
    private int assignedBranch;
    private String userName;
    private String name;

    public User(long id) {
        this.id = id;
    }

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

    public int getAssignedBranch() {
        return assignedBranch;
    }

    public void setAssignedBranch(int assignedBranch) {
        this.assignedBranch = assignedBranch;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
