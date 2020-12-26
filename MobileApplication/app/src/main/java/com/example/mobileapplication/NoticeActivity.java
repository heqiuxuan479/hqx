package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.os.Bundle;

public class NoticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notice);
        super.onCreate(savedInstanceState);

        //在响应程序中实现通知的取消显示
        /*NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);*/
    }
}
