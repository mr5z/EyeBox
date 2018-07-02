package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.PaymentGroup;

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

    @Query("SELECT * FROM Payments WHERE status = 'unsubmitted'")
    List<Payment> getAllUnsubmittedPayments();

    @Query("UPDATE Payments SET status = 'submitted' WHERE status = 'unsubmitted'")
    int markAllSubmitted();

    @Query("SELECT * FROM Payments WHERE id = :paymentId")
    Payment findPaymentById(long paymentId);

    @Query("SELECT * FROM Payments WHERE customerId = :customerId")
    List<Payment> getPaymentsByClientId(long customerId);

    @Query("SELECT " +
            "   customerId," +
            "   status," +
            "   customerName AS clientName, " +
            "   productNumber, " +
            "   checkDate AS payDate, " +
            "   SUM(amount) AS totalPayment " +
            "FROM " +
            "   Payments " +
            "GROUP BY productNumber")
    List<PaymentGroup> getGroupedPayments();

    @Query("SELECT * FROM Payments WHERE productNumber = :productNumber")
    List<Payment> getPaymentsByProductNumber(String productNumber);

    @Query("DELETE FROM Payments WHERE id = :paymentId")
    int deletePayment(long paymentId);

    @Query("DELETE FROM Payments WHERE productNumber = :productNumber")
    int deleteByProductNumber(String productNumber);

    @Query("DELETE FROM Payments")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Payments")
    long count();
}
