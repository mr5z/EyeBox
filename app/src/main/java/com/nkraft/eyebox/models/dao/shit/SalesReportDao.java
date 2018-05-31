package com.nkraft.eyebox.models.dao.shit;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.shit.SalesReport;

import java.util.List;

@Dao
public interface SalesReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesReport(List<SalesReport> salesReport);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesReport(SalesReport salesReport);

    @Update
    void updateSalesReports(List<SalesReport> salesReports);

    @Update
    void updateSalesReport(SalesReport salesReport);

    @Query("SELECT * FROM SalesReport")
    List<SalesReport> getAllSalesReports();

    @Query("SELECT * FROM SalesReport WHERE id = :salesReportId")
    SalesReport findSalesReportById(long salesReportId);

    @Query("DELETE FROM SalesReport")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM SalesReport")
    long count();
}
