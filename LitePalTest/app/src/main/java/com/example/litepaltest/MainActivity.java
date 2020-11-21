package com.example.litepaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Binder;
import android.os.Bundle;
import android.service.autofill.Validator;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentView(R.layout.activity_main);

        Button createDatabase = findViewById(R.id.create_database);
        createDatabase.setOnClickListener(this);
        Button addDate = findViewById(R.id.add_data);
        addDate.setOnClickListener(this);
        Button updateData = findViewById(R.id.update_data);
        updateData.setOnClickListener(this);
        Button delete_data = findViewById(R.id.delete_data);
        delete_data.setOnClickListener(this);
        Button query_data = findViewById(R.id.query_data);
        query_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_database:
                LitePal.getDatabase();
                break;
            case R.id.add_data:
                Album albumToAdd = new Album();
                albumToAdd.setName("The Da Vinici Code");
                albumToAdd.setPrice((float) 16.96);
                albumToAdd.save();
            case R.id.update_data:
                Album albumToUpdate = new Album();
                albumToUpdate.setPrice((float) 20.00);
                albumToUpdate.updateAll("name = ?","The Da Vinici Code");
        }
    }
}
