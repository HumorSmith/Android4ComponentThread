package com.jhzl.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.jhzl.server.Callback;
import com.jhzl.server.Login;
import com.jhzl.server.User;


public class LoginService extends Service {
    public static final String TAG = LoginService.class.getSimpleName();
    public LoginService() {
    }

     class TestBinder extends Login.Stub {

         @Override
         public void login(User stu, Callback call) throws RemoteException {
                stu.setAge(11);
                stu.setName(stu.getName()+" login");
                call.onSuccess(stu);
         }
    }

    TestBinder binder = new TestBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }
}