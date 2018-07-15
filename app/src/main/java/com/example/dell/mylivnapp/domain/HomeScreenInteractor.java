package com.example.dell.mylivnapp.domain;

import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

public interface HomeScreenInteractor {
    List<Item> getItemList();
    boolean deleteItem(Item item);
    void reorderItems(List<Item> items);
    void addItem(Item item);
}
