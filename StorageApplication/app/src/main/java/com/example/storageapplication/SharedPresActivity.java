package com.example.storageapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SharedPresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pres);

        Button save_data = findViewById(R.id.save_data);
        save_data.setOnClickListener(this);
        Button restore_data = findViewById(R.id.restore_data);
        restore_data.setOnClickListener(this);
    }

    @Override
    public void setOnClick(View view){
        switch(view.getId()){
            case R.id.save_data:
                SharedPreferences sharedPreferences = getSharedPreferences("MySharePres",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Name","FHY");
                editor.putInt("Number",20190101);
                editor.apply();
                Log.d("SharedPresActivity","save data......");
                break;
            case R.id.restore_data:
                SharedPreferences pref = getSharedPreferences("MySharePres",MODE_PRIVATE);
                String name = pref.getString("Name","");
                int number = pref.getInt("Number",0);
                Log.d("SharedPresActivity","The name is"+name+", and the is"+number);
                break;
        }
    }

}
