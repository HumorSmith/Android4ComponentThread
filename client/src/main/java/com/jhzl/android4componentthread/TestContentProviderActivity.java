package com.jhzl.android4componentthread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class TestContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_content_provider);
        findViewById(R.id.add_test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertUser();
                    }
                }).start();
            }
        });
    }



    private void insertUser() {
        /**
         * 对user表进行操作
         */

        // 设置URI
        Uri uri_user = Uri.parse("content://com.jhzl.server.contentprovider.TestProvider/user");

        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", System.currentTimeMillis());
        values.put("name", "Iverson");

        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);
    }
}