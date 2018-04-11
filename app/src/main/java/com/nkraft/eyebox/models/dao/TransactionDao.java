package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransactions(List<Transaction> transactions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(Transaction transaction);

    @Update
    void updateTransactions(List<Transaction> transactions);

    @Update
    void updateTransactions(Transaction transaction);

    @Query("SELECT * FROM Transactions")
    List<Transaction> getAllTransactions();

    @Query("SELECT * FROM Transactions WHERE id = :transactionId")
    Transaction findTransactionById(long transactionId);

    @Query("SELECT COUNT(*) FROM Transactions")
    long count();
}
