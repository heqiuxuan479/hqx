package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private String channelId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button send_notice = findViewById(R.id.send_notice);
        send_notice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_notice:
                Intent intent = new Intent(this,NoticeActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

                Notification notification = new NotificationCompat.Builder(this,channelId)
                        .setContentTitle("Title")
                        .setContentText("Content")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .build();
                manager.notify(1,notification);
                break;
                default:
                    break;
        }
    }
}
