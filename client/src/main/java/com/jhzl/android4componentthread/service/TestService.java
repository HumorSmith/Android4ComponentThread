package com.jhzl.android4componentthread.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
    public static final String TAG = TestService.class.getSimpleName();
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand thread name = "+Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate thread name = "+Thread.currentThread().getName());
    }
}