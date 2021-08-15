package com.jhzl.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {
    public static final String TAG = MessengerService.class.getSimpleName();
    public static final int SEND_FROM_SERVER = 2;
    public static final int MESSENGE_FROM_CLIENT = 1;
    static Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSENGE_FROM_CLIENT:
                    Log.i(TAG,"receive msg from Client:" + msg.getData().getString
                            ("msg"));
                    Messenger client = msg.replyTo;
                    Message relpyMessage = Message.obtain(null,SEND_FROM_SERVER);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg","嗯，你的消息我已经收到，稍后会回复你。");
                    relpyMessage.setData(bundle);
                    try {
                        client.send(relpyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    Messenger messenger = new Messenger(mHandler);
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
