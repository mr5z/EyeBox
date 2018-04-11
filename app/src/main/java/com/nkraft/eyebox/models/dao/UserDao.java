package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUsers(List<User> users);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM Users")
    List<User> getAllUsers();

    @Query("SELECT * FROM Users WHERE id = :userId")
    User findUserById(long userId);

    @Query("SELECT COUNT(*) FROM Users")
    long count();
}