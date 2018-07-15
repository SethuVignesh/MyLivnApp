package com.example.dell.mylivnapp.data;

import android.content.Context;

import com.example.dell.mylivnapp.data.cachemodule.PrefUtils;
import com.example.dell.mylivnapp.data.model.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RepositoryImpl implements Repository {
    Context context;

    public RepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<Item>> readItemsAsObservable() {
        return null;
    }

    public List<Item> readItems() {
        //read from cache then from db and them from cloud

        return readFromCache();
    }

    @Override
    public void insertItems(List<Item> itemList) {
        insertToCache(itemList);
        insertToDb(itemList);
        insertToCloud(itemList);

    }

    @Override
    public Observable<Item> readItem() {
        return null;
    }

    @Override
    public void insertItem(Item item) {
        insertToCache(item);
        insertToDb(item);
        insertToCloud(item);

    }

    @Override
    public void deleteItems(String... ids) {

    }

    @Override
    public boolean deleteItem(Item item) {
        return deleteInCache(item);
//        deleteInDb(id);
//        deleteInCloud(id);
    }

    private boolean deleteInCache(Item item) {
        ArrayList<Item> arrayList = (ArrayList) PrefUtils.getItems(context);
        if (arrayList.contains(item)) {
            arrayList.remove(item);
            PrefUtils.saveItems(arrayList, context);
            return true;
        }
       return  false;
    }


    public Observable<List<Item>> readFromDb() {
        return null;
    }

    public List<Item> readFromCache() {
        return PrefUtils.getItems(context);
    }

    public Observable<List<Item>> readFromCloud() {
        return null;
    }

    public void insertToDb(List<Item> itemList) {

    }

    public void insertToDb(Item item) {

    }

    public void insertToCache(List<Item> arrayList) {
        PrefUtils.saveItems(arrayList, context);
    }

    public void insertToCache(Item item) {
        ArrayList list = (ArrayList) PrefUtils.getItems(context);
        list.add(item);
        PrefUtils.saveItems(list, context);
    }


    public void insertToCloud(List<Item> itemList) {

    }

    public void insertToCloud(Item item) {

    }
}