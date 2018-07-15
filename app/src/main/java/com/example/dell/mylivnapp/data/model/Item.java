package com.example.dell.mylivnapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Comparator;

@Entity(tableName = "Items")
public class Item implements Comparator<Item> {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uuid")
    String uuid;
    @ColumnInfo(name = "url")
    String imageUrlString;

    public Item(String uuid, String imageUrlString) {
        this.uuid = uuid;
        this.imageUrlString = imageUrlString;
    }

    public String getUuid() {

        return uuid;
    }

    public String getImageUrlString() {
        return imageUrlString;
    }



    @Override
    public int compare(Item o1, Item o2) {
        return o1.getUuid().compareTo(o2.getUuid());
    }
}
