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

import com.jhzl.android4componentthread.service.TestAIDLServiceActivity;
import com.jhzl.android4componentthread.service.TestIntentService;
import com.jhzl.android4componentthread.service.TestMessengerActivity;
import com.jhzl.android4componentthread.service.TestProcessService;
import com.jhzl.android4componentthread.service.TestService;
import com.jhzl.server.Callback;
import com.jhzl.server.Login;
import com.jhzl.server.User;

public class ServiceActivity extends AppCompatActivity {
    public static final String TAG = ServiceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        findViewById(R.id.test_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(ServiceActivity.this, TestService.class));
            }
        });

        findViewById(R.id.test_intentService_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(ServiceActivity.this, TestIntentService.class));
            }
        });

        findViewById(R.id.test_other_process_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(ServiceActivity.this, TestProcessService.class), innerAppServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.test_aidl_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestAIDLServiceActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test_messenger_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestMessengerActivity.class);
                startActivity(intent);
            }
        });


    }


    private ServiceConnection innerAppServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected processName = " + TestProcessService.getProcessName(getApplicationContext()));
            Log.d(TAG, "onServiceConnected " + Thread.currentThread().getName());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected name = " + name.getClassName());

        }
    };
}