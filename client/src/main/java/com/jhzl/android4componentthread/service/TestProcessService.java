package com.jhzl.android4componentthread.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class TestProcessService extends Service {
    public static final String TAG = TestProcessService.class.getSimpleName();

    public TestProcessService() {
    }
    public class TestBinder extends Binder {

        public TestProcessService getService(){
            Log.d(TAG,"getService thread name = "+Thread.currentThread().getName());
            return TestProcessService.this;
        }

    }

    //通过binder实现调用者client与Service之间的通信
    private TestBinder testBinder = new TestBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind thread name = "+Thread.currentThread().getName());
        Log.d(TAG,"Process Name = "+getProcessName(getApplicationContext()));
        return testBinder;
    }

    public static String getProcessName(Context cxt) {
        int pid = android.os.Process.myPid();
        return pid+"";
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