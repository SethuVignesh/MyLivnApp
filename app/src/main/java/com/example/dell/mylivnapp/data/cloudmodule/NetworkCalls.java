package com.example.dell.mylivnapp.data.cloudmodule;

import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.example.dell.mylivnapp.data.model.Item;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

@Module
@Singleton
public class NetworkCalls {
    private static NetworkCalls networkCalls = new NetworkCalls();

    private NetworkCalls() {

    }

    @Provides
    public static NetworkCalls getNetworkCalls() {
        return networkCalls;
    }


    public Observable<List<Item>> getImageList(Context context, int page, String apiKey) {

        ApiInterface apiService =
                ApiClient.getRetrofit(context).create(ApiInterface.class);
        Observable<List<Item>> observable = apiService.getImageList(page, apiKey);
        return observable;
    }


}
