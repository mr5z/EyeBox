package com.nkraft.eyebox.services.repositories;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Order;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.models.Transaction;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.models.dao.ClientsDao;
import com.nkraft.eyebox.models.dao.OrdersDao;
import com.nkraft.eyebox.models.dao.PaymentsDao;
import com.nkraft.eyebox.models.dao.ProductsDao;
import com.nkraft.eyebox.models.dao.TransactionsDao;
import com.nkraft.eyebox.models.dao.UserDao;

@Database(
    version = 1,
    entities = {
        User.class,
        Product.class,
        Transaction.class,
        Client.class,
        Payment.class,
        Order.class
    }
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao users();
    public abstract ProductsDao products();
    public abstract ClientsDao clients();
    public abstract TransactionsDao transactions();
    public abstract PaymentsDao payments();
    public abstract OrdersDao orders();

    private static AppDatabase _instance;
    public static AppDatabase instance(Context context) {
        if (_instance == null) {
            _instance = Room.databaseBuilder(context, AppDatabase.class, "eyebox").build();
        }
        return _instance;
    }
}
