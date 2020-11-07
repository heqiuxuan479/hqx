package com.example.activityapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Button normalBtn =findViewById(R.id.start_normal_activity);
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent1);
            }
        });
        Button dilogBtn = findViewById(R.id.start_dialog_activity);
        dilogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent2);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","调用onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","调用onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","调用onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","调用onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","调用onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity","调用onRestart");
    }
}
