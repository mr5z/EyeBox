package com.nkraft.eyebox.models.dao.shit;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.shit.Bank;

import java.util.List;

@Dao
public interface BanksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBanks(List<Bank> banks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBank(Bank bank);

    @Update
    void updateBanks(List<Bank> banks);

    @Update
    void updateBank(Bank bank);

    @Query("SELECT * FROM Banks")
    List<Bank> getAllBanks();

    @Query("SELECT * FROM Banks WHERE id = :bankId")
    Bank findBankById(long bankId);

    @Query("DELETE FROM Banks")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Banks")
    long count();
}
