package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Credit;

import java.util.List;

@Dao
public interface CreditsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCredit(List<Credit> credits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCredit(Credit credit);

    @Update
    void updateCredits(List<Credit> credits);

    @Update
    void updateCredit(Credit credit);

    @Query("SELECT * FROM Credits")
    List<Credit> getAllCredits();

    @Query("SELECT * FROM Credits WHERE id = :creditId")
    Credit findCreditById(long creditId);

    @Query("DELETE FROM Credits")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Credits")
    long count();
}
