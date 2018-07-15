package com.example.dell.mylivnapp.data.cloudmodule;

import com.example.dell.mylivnapp.data.model.Item;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(".")
    Observable<List<Item>> getImageList(@Query("page") int p, @Query("apikey") String apiKey);


}

