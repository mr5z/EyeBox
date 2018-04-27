package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Order;

import java.util.List;

@Dao
public interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrders(List<Order> orders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(Order order);

    @Update
    void updateOrders(List<Order> orders);

    @Update
    void updateOrder(Order order);

    @Query("SELECT * FROM Orders")
    List<Order> getAllOrders();

    @Query("SELECT * FROM Orders WHERE id = :orderId")
    Order findOrderById(long orderId);

    @Query("DELETE FROM Orders")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Orders")
    long count();
}
