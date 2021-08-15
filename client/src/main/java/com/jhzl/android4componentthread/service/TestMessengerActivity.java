package com.jhzl.android4componentthread.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jhzl.android4componentthread.R;

public class TestMessengerActivity extends AppCompatActivity {
    public static final String TAG = TestMessengerActivity.class.getSimpleName();
    public static final int SEND_FROM_SERVER = 2;
    public static final int RECEIVE_FROM_CLIENT = 1;
    private boolean isServiceConnect = false;
    private Messenger mMessenger;

    public static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SEND_FROM_SERVER:
                    Object data = msg.getData().get("msg");
                    Log.d(TAG,"SEND_FROM_SERVER data = >"+data);

                    break;
            }
        }
    }

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_messeger);
        findViewById(R.id.bind_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServiceConnect) {
                    return;
                }
                Intent intent = new Intent();
                intent.setPackage("com.jhzl.server");
                intent.setAction("com.jhzl.messenger");
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.send_messenger_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = RECEIVE_FROM_CLIENT;
                Bundle bundle = new Bundle();
                bundle.putString("msg","msg from client");
                message.setData(bundle);
                message.replyTo = mGetReplyMessenger;
                try {
                    mMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Toast.makeText(TestMessengerActivity.this, "绑定成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}