package id.codecast.roomexample.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.codecast.roomexample.entity.Product;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id = :id")
    Product getAll(long id);

    @Insert
    void create(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);
}
