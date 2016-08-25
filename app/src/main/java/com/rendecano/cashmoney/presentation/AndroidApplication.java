package com.rendecano.cashmoney.presentation;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ren Decano.
 */

public class AndroidApplication extends Application {

    private static AndroidApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getApplicationInstance() {
        return instance;
    }
}
