package com.jhzl.android4componentthread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.jhzl.android4componentthread.service.TestIntentService;
import com.jhzl.android4componentthread.service.TestProcessService;
import com.jhzl.android4componentthread.service.TestService;
import com.jhzl.server.hello_aidl;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, TestService.class));
            }
        });

        findViewById(R.id.test_intentService_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, TestIntentService.class));
            }
        });

        findViewById(R.id.test_other_process_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(MainActivity.this, TestProcessService.class), innerAppServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.test_content_provider_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestContentProviderActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.test_aidl_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServiceConnect){
                    try {
                        mHelloAIDL.hello();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Intent intent = new Intent();
                intent.setPackage("com.jhzl.server");
                intent.setAction("com.jhzl.hello");
                bindService(intent,serverServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }


    private boolean isServiceConnect = false;
    hello_aidl mHelloAIDL;
    private ServiceConnection serverServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isServiceConnect = true;
            mHelloAIDL = com.jhzl.server.hello_aidl.Stub.asInterface(service);
            try {
                mHelloAIDL.hello();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceConnect = false;
        }
    };

    private ServiceConnection innerAppServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected processName = "+ TestProcessService.getProcessName(getApplicationContext()));
            Log.d(TAG,"onServiceConnected "+Thread.currentThread().getName() );
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected name = "+name.getClassName());

        }
    };
}