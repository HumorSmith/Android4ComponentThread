package com.jhzl.android4componentthread.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestIntentService extends IntentService {
    public static final String TAG = TestIntentService.class.getSimpleName();

    public TestIntentService() {
        super(TAG);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TestIntentService(String name) {
        super(name);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG,"onHandleIntent thread name = "+Thread.currentThread().getName());
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