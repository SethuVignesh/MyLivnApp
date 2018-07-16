package com.example.dell.mylivnapp.di;


import com.example.dell.mylivnapp.data.RepositoryImpl;
import com.example.dell.mylivnapp.data.cachemodule.PrefUtils;
import com.example.dell.mylivnapp.data.cloudmodule.NetworkCalls;

import dagger.Component;
import retrofit2.Retrofit;
@Component(modules ={PrefUtils.class, NetworkCalls.class, RepositoryImpl.class})
public interface Persistance {

    PrefUtils getPrefUtils();
    NetworkCalls getNetworkCalls();
}
