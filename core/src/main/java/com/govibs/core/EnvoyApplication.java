package com.govibs.core;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Main Application Class
 * Created by Vibhor on 12/7/17.
 */

public class EnvoyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
