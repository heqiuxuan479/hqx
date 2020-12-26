package com.example.heqiuxuan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_1;
    private String username, usernumber;
    // private EditText et_2;
    private ImageView iv_pic;
    private Button btn_1;//联系人
    private Button btn_2;//拨打
    private Button btn_3;//短信
    private Button btn_4;//相机
    private Button btn_5;//地图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_1 = (EditText) findViewById(R.id.et_1);

        iv_pic = findViewById(R.id.iv_pic);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
    }

    private static int REQUEST_CAMERA = 3;
    private static int PICK_CONTACT = 0;

    @Override
    public void onClick(View view) {
        //Intent intent = new Intent();
        if (view.getId() == R.id.btn_1) {//联系人
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        } else if (view.getId() == R.id.btn_2) {//拨打电话
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_CALL);
            intent2.addCategory(Intent.CATEGORY_DEFAULT);
            String num = et_1.getText().toString();
            intent2.setData(Uri.parse("tel:" + num));
            startActivity(intent2);

        } else if (view.getId() == R.id.btn_4) {//相机
            Intent intent1 = new Intent();
            intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE); //设置动作为调用照相机
            startActivityForResult(intent1, 3);//REQUEST_CAMERA请求码

        } else if (view.getId() == R.id.btn_3) {//短信
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:10086"));
            intent.putExtra("sms_body","发短信：41812540何秋璇");
            startActivity(intent);
        }
        else if (view.getId() == R.id.btn_5) {
            Intent intent5 = new Intent();
            Uri uri=Uri.parse("https://m.baidu.com/");
            Uri uri1=Uri.parse("geo:39.899533,116.036476");//打开百度地图
            intent5.setAction(Intent.ACTION_VIEW);
            intent5.setData(uri1);
            startActivity(intent5);
        }
    }

    //在返回函数中
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getExtras();
        if (data == null)
            return;
        super.onActivityResult(requestCode, resultCode, data);
        //Bundle bundle = data.getExtras();// 从data中取出传递回来的数据
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    ContentResolver reContentResolverol = getContentResolver();
                    Uri contactData = data.getData();
                    Cursor cursor = managedQuery(contactData, null, null, null, null);
                    cursor.moveToFirst();
                    username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phone = reContentResolverol.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phone.moveToNext()) {
                        usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        et_1.setText(usernumber + " (" + username + ")");
                    }

                }
                break;
            case 3:
                Bitmap b = (Bitmap) bundle.get("data"); //将data中的信息流解析为Bitmap类型
                iv_pic.setImageBitmap(b);// 显示图片
                break;
            default:
                break;
        }
    }
}
