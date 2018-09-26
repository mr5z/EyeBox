package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Order2;

import java.util.List;

@Dao
public interface OrdersDao2 {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrders(List<Order2> orders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(Order2 order);

    @Update
    void updateOrders(List<Order2> orders);

    @Update
    void updateOrder(Order2 order);

    @Query("SELECT * FROM Orders2")
    List<Order2> getAllOrders();

    @Query("SELECT * FROM Orders2 WHERE id = :orderId")
    Order2 findOrderById(long orderId);

    @Query("DELETE FROM Orders2")
    int deleteAll();

    @Query("DELETE FROM Orders2 WHERE id = :id")
    int deleteById(long id);

    @Query("SELECT COUNT(*) FROM Orders2")
    long count();
}
