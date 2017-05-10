package com.ma.readerdemo;

import android.util.Log;

import com.koolearn.klibrary.ui.android.library.ZLAndroidApplication;


public class MyApplication  extends ZLAndroidApplication {
    static String LOG_TAG = "NineStars";
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("TAG", LOG_TAG);
    }
}
