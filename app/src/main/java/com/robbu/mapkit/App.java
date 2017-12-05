package com.robbu.mapkit;

import android.app.Application;

import com.robbu.mapkit.map.common.MapHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MapHelper.init(this);
    }
}
