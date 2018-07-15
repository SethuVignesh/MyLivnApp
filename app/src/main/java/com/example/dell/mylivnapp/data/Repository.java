package com.example.dell.mylivnapp.data;

import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    public Observable<List<Item>> readItemsAsObservable();

    public List<Item> readItems();

    public void insertItems(List<Item> itemList);

    public Observable<Item> readItem();

    public void insertItem(Item item);

    public void deleteItems(String... ids);

    public boolean deleteItem(Item item);


}
