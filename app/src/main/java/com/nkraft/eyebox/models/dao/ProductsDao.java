package com.nkraft.eyebox.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nkraft.eyebox.models.Product;

import java.util.List;

@Dao
public interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(List<Product> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);

    @Update
    void updateProducts(List<Product> products);

    @Update
    void updateProducts(Product product);

    @Query("SELECT * FROM Products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM Products WHERE id = :productId")
    Product findProductById(long productId);

    @Query("SELECT COUNT(*) FROM Products")
    long count();
}
