package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Payment;

import java.util.List;

@Dao
public interface PaymentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPayments(List<Payment> payments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPayment(Payment transaction);

    @Update
    void updatePayments(List<Payment> payments);

    @Update
    void updatePayments(Payment transaction);

    @Query("SELECT * FROM Payments")
    List<Payment> getAllPayments();

    @Query("SELECT * FROM Payments WHERE id = :paymentId")
    Payment findPaymentById(long paymentId);

    @Query("SELECT * FROM Payments WHERE customerId = :customerId")
    List<Payment> getPaymentsByClientId(long customerId);

    @Query("DELETE FROM Payments WHERE id = :paymentId")
    int deletePayment(long paymentId);

    @Query("SELECT COUNT(*) FROM Payments")
    long count();
}
