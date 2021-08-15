package com.jhzl.android4componentthread.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jhzl.android4componentthread.R;
import com.jhzl.android4componentthread.TestContentProviderActivity;
import com.jhzl.server.Callback;
import com.jhzl.server.Login;
import com.jhzl.server.User;

public class TestAIDLServiceActivity extends AppCompatActivity {
    public static final String TAG = TestContentProviderActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aidl_service);

        findViewById(R.id.test_login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServiceConnect){
                    try {
                        User user = new User();
                        user.setName("xiaohuihui");
                        user.setAge(1);
                        mLogin.login(user, new Callback.Stub() {
                            @Override
                            public void onSuccess(User user) throws RemoteException {
                                Log.d(TAG, "login onSuccess =>" + Thread.currentThread().getName());
                                Toast.makeText(TestAIDLServiceActivity.this, "登录用户=>" + user.toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailed(int errorCode, String errorMsg) throws RemoteException {

                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        });
        findViewById(R.id.test_aidl_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServiceConnect){
                    return;
                }
                Intent intent = new Intent();
                intent.setPackage("com.jhzl.server");
                intent.setAction("com.jhzl.server");
                bindService(intent, aidlServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.test_recipient_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mLogin.asBinder().linkToDeath(deathRecipient,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            //死亡时会通知过来，可以在这里做释放或者重连操作
        }
    };

    private boolean isServiceConnect = false;
    Login mLogin;
    private ServiceConnection aidlServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"aidlServiceConnection onServiceConnected =>"+Thread.currentThread().getName());
            isServiceConnect = true;
            mLogin = Login.Stub.asInterface(service);
            //binder是否还连接着
            Toast.makeText(TestAIDLServiceActivity.this,"绑定服务成功",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceConnect = false;
        }
    };
}