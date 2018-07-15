package com.example.dell.mylivnapp.presentation.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.dell.mylivnapp.AppApplication;
import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

public class ItemsViewModel extends ViewModel {
    private List<Item> itemList;

    public List<Item> getItemList() {
        if (itemList == null) {
            itemList = loadItems();
        }
        return itemList;
    }

    private List<Item> loadItems() {
        // do something to load users
        return AppApplication.homeScreenInteractor.getItemList();
    }
}