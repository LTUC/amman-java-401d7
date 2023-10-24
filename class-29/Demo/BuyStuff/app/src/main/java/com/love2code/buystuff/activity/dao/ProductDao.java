package com.love2code.buystuff.activity.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.love2code.buystuff.activity.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    public void insertAProduct(Product product);

    @Query("select * from Product")
    public List<Product> findAll();

    @Query("select * from Product ORDER BY name ASC")
    public List<Product> findAllSortedByName();

    @Query("select * from Product where id = :id")
    Product findByAnId(long id);
}
