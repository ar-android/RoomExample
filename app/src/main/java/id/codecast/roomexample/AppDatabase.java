package id.codecast.roomexample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import id.codecast.roomexample.dao.ProductDao;
import id.codecast.roomexample.entity.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract ProductDao getProductDao();
}
