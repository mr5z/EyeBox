package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Sale;

import java.util.List;

@Dao
public interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSales(List<Sale> sales);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSale(Sale client);

    @Update
    void updateSales(List<Sale> sales);

    @Update
    void updateSale(Sale client);

    @Query("SELECT * FROM Sales")
    List<Sale> getAllSales();

    @Query("SELECT * FROM Sales WHERE id = :salesId")
    Sale findSaleById(long salesId);

    @Query("SELECT * FROM Sales WHERE customerId = :customerId")
    List<Sale> getActiveSalesByCustomerId(String customerId);

    @Query("DELETE FROM Sales")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Sales")
    long count();
}
