package com.love2code.buystuff.activity.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.love2code.buystuff.activity.dao.ProductDao;
import com.love2code.buystuff.activity.model.Product;

@Database(entities = {Product.class}, version = 2)
@TypeConverters({BuyMoreStuffDatabaseConverters.class})
public abstract class BuyMoreStuffDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
}
