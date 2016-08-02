package com.meiriq.xposehook;

import android.app.Application;

/**
 * Created by tian on 15-12-2.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        SP.init(this);
    }
}
