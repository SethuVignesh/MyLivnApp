package com.example.dell.mylivnapp.data.cloudmodule;

import android.content.Context;

import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

import io.reactivex.Observable;

public class NetworkCalls {
    private static NetworkCalls networkCalls = new NetworkCalls();

    private NetworkCalls() {

    }

    public static NetworkCalls getInstance() {
        return networkCalls;
    }


    public Observable<List<Item>> getImageList(Context context, int page,String apiKey ) {

        ApiInterface apiService =
                ApiClient.getRetrofit(context).create(ApiInterface.class);
        Observable<List<Item>> observable = apiService.getImageList(page , apiKey);
        return observable;
    }
}
