package com.example.dell.mylivnapp.presentation.viewmodel;


import java.util.List;

import static com.example.dell.mylivnapp.presentation.viewmodel.Status.ERROR;
import static com.example.dell.mylivnapp.presentation.viewmodel.Status.LOADING;
import static com.example.dell.mylivnapp.presentation.viewmodel.Status.SUCCESS;

public class Response {

    public final Status status;


    public final List<Item> data;


    public final Throwable error;

    private Response(Status status, List<Item> data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }

    public static Response success(List<Item> data) {
        return new Response(SUCCESS, data, null);
    }

    public static Response error(Throwable error) {
        return new Response(ERROR, null, error);
    }

}