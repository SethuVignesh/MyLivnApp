package com.example.dell.mylivnapp.domain;

import android.content.Context;

import com.example.dell.mylivnapp.data.Repository;
import com.example.dell.mylivnapp.data.RepositoryImpl;
import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

public class HomeScreenInteractorImpl implements HomeScreenInteractor {

    Context context;
    Repository repository;

    public HomeScreenInteractorImpl(Context context, RepositoryImpl repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    public List<Item> getItemList() {
        return repository.readItems();
    }

    @Override
    public boolean deleteItem(Item item) {
        return repository.deleteItem(item);
    }

    @Override
    public void reorderItems(List<Item> items) {
        repository.insertItems(items);
    }

    @Override
    public void addItem(Item item) {
       repository.insertItem(item);
    }
}
