package com.example.broadcastapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LocalBroadcastActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localbroadcast_receiver);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);  //获取实例
        Button button_lbc = findViewById(R.id.button_localBC);
        button_lbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcastapplication.LocalBroadcast");
                localBroadcastManager.sendBroadcast(intent);   // 发送本地广播
            }
        });

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcastapplication.LocalBroadcast");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);  //注册本地广播监听器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("LocalBroadcastActivity", "This is a LocalBroadcastReceiver!");
        }
    }
}
