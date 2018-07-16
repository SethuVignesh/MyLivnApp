package com.example.dell.mylivnapp.data.cachemodule;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.dell.mylivnapp.data.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class PrefUtils {

    private static final String FAV_LIST = "fav";
    public static String PREF_FILE_NAME = "noon_academy_pref";
@Provides
    public PrefUtils getPrefUtils() {
        return new PrefUtils();

    }

    public synchronized static void saveItems(List<Item> bookMarked, @NonNull Context context) {

        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookMarked);
        prefrencesEditor.putString(FAV_LIST, json);
        prefrencesEditor.commit();
    }

    public static synchronized List<Item> getItems(@NonNull Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString(FAV_LIST, null);
        Type type = new TypeToken<List<Item>>() {
        }.getType();
        List<Item> arrayList = gson.fromJson(json, type);
        if (arrayList == null) return new ArrayList<>();
        return arrayList;
    }
}
