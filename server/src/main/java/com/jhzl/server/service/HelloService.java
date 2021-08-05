package com.jhzl.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.jhzl.server.hello_aidl;

public class HelloService extends Service {
    public static final String TAG = HelloService.class.getSimpleName();
    public HelloService() {
    }

     class TestBinder extends hello_aidl.Stub {


        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void hello() throws RemoteException {
            Log.d(TAG,"hello  thread = >"+Thread.currentThread().getName());
        }
    }

    TestBinder binder = new TestBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }
}