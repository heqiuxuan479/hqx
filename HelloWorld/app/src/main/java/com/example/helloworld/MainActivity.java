package com.example.helloworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private String[] mountainArray;
    private ArrayList<String> hobbyList = new ArrayList<>();

    private EditText usernameView;
    private TextView resultView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mountainArray = getResources().getStringArray(R.array.visit_place_array);
        initView();
    }

    private void initView() {
        TextView placeLabelView = findViewById(R.id.place_label);
        placeLabelView.setText("请投票");

        RadioGroup rg = findViewById(R.id.visit_place_rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int index =-1;
                if (i ==R.id.place1) {
                    index = 0;
                }else if (i == R.id.place2) {
                    index = 1;
                }else if (i == R.id.place3) {
                    index = 2;
                }else if (i == R.id.place4) {
                    index = 3;
                }

                if (index >=0 && index < mountainArray.length) {
                    showUIToast(mountainArray[index]);
                }
            }
        });

        CheckBox badmintonView = (CheckBox)findViewById(R.id.badminton_view);
        CheckBox basketballView = (CheckBox)findViewById(R.id.basketball_view);

        badmintonView.setOnCheckedChangeListener(this);
        basketballView.setOnCheckedChangeListener(this);
        usernameView = (EditText) findViewById(R.id.username);
        resultView = (TextView) findViewById(R.id.result_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button plusView = (Button) findViewById(R.id.plus_view);
        Button substractView = (Button) findViewById(R.id.substract_view);
        Button submitView = (Button) findViewById(R.id.submit);

        plusView.setOnClickListener(this);

        /*plusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        plusView.setOnClickListener(this);
        substractView.setOnClickListener(this);
        submitView.setOnClickListener(this);
    }

    private void showUIToast(final String info){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "您投票的景点是：" + info,
                        Toast.LENGTH_LONG).show();
            }
            });
    }


    private void onSubmitConfirm() {
        AlertDialog dlg;
        AlertDialog.Builder builder= new AlertDialog.Builder(this)
                .setTitle("普通对话框")    //设置对话框标题
                .setIcon(R.mipmap.ic_launcher)  //设置标题图标
                .setMessage("是否确定提交？")     //设置对话框提示信息
                //添加“确定”按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onSubmit();
                    }
                })
                //添加“取消”按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        dlg = builder.create();
        dlg.show();
    }

    private void onSubmit() {
        StringBuffer sb =new StringBuffer();
        sb.append(getString(R.string.your_hobbies_info,hobbyList.toString()));
        String username = usernameView.getText().toString();
        if (username != null && !username.isEmpty()) {
            sb.append("\n by" + username);
        }
        resultView.setText(sb.toString());
    }

    private void updateProgressView(boolean plus) {
        int progress = progressBar.getProgress();
        if (plus) {
            progress += 10;
            if(progress > 100) {
                progress = 100;
            }
        }else {
            progress -= 10;
            if (progress < 0) {
                progress = 0;
            }
        }

        progressBar.setProgress(progress);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.plus_view:
                updateProgressView(true);
                break;
            case R.id.submit:
                onSubmitConfirm();
                break;
            case R.id.substract_view:
                updateProgressView(false);
                break;
            default:
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton,boolean checked) {
        String hobby = compoundButton.getText().toString();
        if (checked) {
            if (!hobbyList.contains(hobby)) {
                hobbyList.add(hobby);
            }
        }else {
            if (hobbyList.contains(hobby)) {
                hobbyList.remove(hobby);
            }
        }
    }
}
