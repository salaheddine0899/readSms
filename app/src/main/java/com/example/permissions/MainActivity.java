package com.example.permissions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void load(View view) {
        if((int) Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
                if(!shouldShowRequestPermissionRationale( Manifest.permission.READ_SMS)){
                    requestPermissions(new String[]{Manifest.permission.READ_SMS},1);
                }
                return;
            }

        }
        loadInboxMessages();
    }
    void loadInboxMessages(){
        try {
            int phoneIndex,msgIndex;
            String sms = "";
            Uri uriSms = Uri.parse("content://sms/inbox");
            Cursor cur = getContentResolver().query(uriSms, null, null, null, null);
            cur.moveToPosition(0);
            while (cur.moveToNext()) {
                phoneIndex=cur.getColumnIndex("address");
                msgIndex=cur.getColumnIndex("body");
                if(phoneIndex>=0&&msgIndex>=0) {
                    sms += "from:" + cur.getString(phoneIndex) + " : " + cur.getString(msgIndex) + "\n";
                }
            }
            TextView tv=(TextView) findViewById(R.id.tv);
            tv.setText(sms);
        }catch (Exception ex){

        }

        }
    }