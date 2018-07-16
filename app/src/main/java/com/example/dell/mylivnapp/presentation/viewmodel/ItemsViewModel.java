package com.example.dell.mylivnapp.presentation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dell.mylivnapp.AppApplication;
import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

public class ItemsViewModel extends ViewModel {
    public MutableLiveData<List<Item>> itemList;

    public MutableLiveData<List<Item>> getItemList() {
        if (itemList == null) {
            itemList = new MutableLiveData<>();

        }
        itemList.postValue(loadItems());
        return itemList;
    }

    public void updateItemList(List<Item> items) {
        AppApplication.homeScreenInteractorImpl.reorderItems(items);
        itemList.postValue(items);
    }


    private List<Item> loadItems() {
        return AppApplication.homeScreenInteractorImpl.getItemList();
    }
}