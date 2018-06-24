package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Client;

import java.util.List;

@Dao
public interface ClientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClients(List<Client> clients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClient(Client client);

    @Update
    void updateClients(List<Client> clients);

    @Update
    void updateClient(Client client);

    @Query("SELECT * FROM Clients")
    List<Client> getAllClients();

    @Query("SELECT * FROM Clients WHERE id = :clientId")
    Client findClientById(long clientId);

//    @Query("SELECT " +
//            "    id, " +
//            "    name, " +
//            "    address " +
//            "    (SUM(Sales.totalamount) - SUM(Sales.payamount)) AS totalcredit " +
//            "FROM " +
//            "    Clients, Sales " +
//            "WHERE " +
//            "    Clients.id = Sales.customerid " +
//            "AND " +
//            "    Clients.branchlink = :branchLink " +
//            "AND " +
//            "    Sales.branchno = :branchLink " +
//            "AND " +
//            "    Sales.duedate <> 0 " +
//            "AND " +
//            "    (Sales.totalamount - Sales.payamount) > 0" +
//            "GROUP BY " +
//            "    Clients.id")
//    List<Client> getActiveClients(int branchLink);
    @Query("DELETE FROM Clients")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Clients")
    long count();
}
