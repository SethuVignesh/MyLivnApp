package com.example.dell.mylivnapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyView extends RelativeLayout {
    ImageView imageView;
    ImageView closeBtn;
    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private void init() {
        inflate(getContext(), R.layout.custom_views, this);
        imageView=findViewById(R.id.imageView);
        closeBtn=findViewById(R.id.xcross);
        //Create your layout here
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        imageView = (ImageView) this
//                .findViewById(R.id.imageView);
//
//
//        closeBtn = (ImageView) this
//                .findViewById(R.id.xcross);
    }
}