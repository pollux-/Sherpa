package com.pollux.sharpa.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shabaz on 06-Feb-16.
 */
public class MyApplication extends Application
{
    public static Context context;
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
    }
}
