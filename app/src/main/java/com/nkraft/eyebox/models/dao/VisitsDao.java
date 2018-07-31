package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Visit;

import java.util.List;

@Dao
public interface VisitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVisits(List<Visit> visits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVisit(Visit visit);

    @Update
    void updateVisits(List<Visit> visits);

    @Update
    void updateVisit(Visit user);

    @Query("SELECT * FROM Visits")
    List<Visit> getAllVisits();

    @Query("SELECT * FROM Visits WHERE customerId = :customerId")
    Visit findVisitByCustomerId(long customerId);

    @Query("DELETE FROM Visits WHERE id = :id")
    int deleteById(long id);

    @Query("DELETE FROM Visits")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Visits")
    long count();
}
