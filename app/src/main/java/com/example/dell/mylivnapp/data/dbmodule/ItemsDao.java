package com.example.dell.mylivnapp.data.dbmodule;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

@Dao
public interface ItemsDao {
    @Query("SELECT * FROM items")
    List<Item> getAll();

    @Query("SELECT COUNT(*) from items")
    int countItems();

    @Insert
    void insertAll(Item... items);

    @Delete
    void delete(Item item);
}
