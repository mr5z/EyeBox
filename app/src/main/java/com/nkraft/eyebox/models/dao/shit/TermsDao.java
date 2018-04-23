package com.nkraft.eyebox.models.dao.shit;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.shit.Terms;

import java.util.List;

@Dao
public interface TermsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerms(List<Terms> terms);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerms(Terms terms);

    @Update
    void updateClients(List<Terms> terms);

    @Update
    void updateTerms(Terms terms);

    @Query("SELECT * FROM Terms")
    List<Terms> getAllTerms();

    @Query("SELECT * FROM Terms WHERE id = :termsId")
    Terms findTermsById(long termsId);

    @Query("SELECT COUNT(*) FROM Terms")
    long count();
}
