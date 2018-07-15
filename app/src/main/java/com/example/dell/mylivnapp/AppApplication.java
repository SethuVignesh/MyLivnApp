package com.example.dell.mylivnapp;

import android.app.Application;

import com.example.dell.mylivnapp.data.Repository;
import com.example.dell.mylivnapp.data.RepositoryImpl;
import com.example.dell.mylivnapp.data.cachemodule.PrefUtils;
import com.example.dell.mylivnapp.data.model.Item;
import com.example.dell.mylivnapp.domain.HomeScreenInteractor;
import com.example.dell.mylivnapp.domain.HomeScreenInteractorImpl;

import java.util.ArrayList;

public class AppApplication extends Application {
    public RepositoryImpl repository;
    public static HomeScreenInteractorImpl homeScreenInteractorImpl;


    @Override
    public void onCreate() {
        super.onCreate();

        prepareDummyData();
        repository = new RepositoryImpl(getApplicationContext());

        homeScreenInteractorImpl = new HomeScreenInteractorImpl(getApplicationContext(), repository);

    }

    private void prepareDummyData() {
        if (PrefUtils.getItems(getApplicationContext()).size() != 0) return;
        ArrayList<Item> arrayList = new ArrayList<>();
        //https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/zero.png
        Item item = new Item("0", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/zero.png");
        Item item1 = new Item("1", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/one.png");
        Item item2 = new Item("2", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/two.png");
        Item item3 = new Item("3", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/three.png");
        Item item4 = new Item("4", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/four.png");
        Item item5 = new Item("5", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/five.png");
        Item item6 = new Item("6", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/six.png");
        Item item7 = new Item("7", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/seven.png");
        Item item8 = new Item("8", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/eight.png");
        Item item9 = new Item("9", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/nine.png");
        Item item10 = new Item("10", "https://raw.githubusercontent.com/SethuVignesh/RecyclerInfiniteScroll/master/app/src/main/res/drawable/ten.png");
        arrayList.add(item);
        arrayList.add(item1);
        arrayList.add(item2);
        arrayList.add(item3);
        arrayList.add(item4);
        arrayList.add(item5);
        arrayList.add(item6);
        arrayList.add(item7);
        arrayList.add(item8);
        arrayList.add(item9);
        arrayList.add(item10);

        PrefUtils.saveItems(arrayList, getApplicationContext());
    }
}
