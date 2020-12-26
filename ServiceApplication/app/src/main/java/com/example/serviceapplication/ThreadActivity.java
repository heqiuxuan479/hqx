package com.example.serviceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private View myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        myText = findViewById(R.id.mytext);
        Button change_text = findViewById(R.id.change_text);
        change_text.setOnClickListener(this);
        Button change_bd = findViewById(R.id.change_bg);
        change_bd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
