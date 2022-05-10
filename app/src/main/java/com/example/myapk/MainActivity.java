package com.example.myapk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText user = findViewById(R.id.user);
        EditText psd = findViewById(R.id.psd);
        //先设置账号密码方便调试
        user.setText("1");
        psd.setText("1");
        //**
        Button b1 = findViewById(R.id.logbtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String users=user.getText().toString();
                String psds= psd.getText().toString();
              if(users.equals("1") && psds.equals("1")){
                  Toast.makeText(MainActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();

                 // Intent it1 = new Intent(MainActivity.this,MainActivity2.class);
                 // startActivity(it1);
                 // Intent t1 = new Intent(MainActivity.this,MainPage.class);
                  Intent t1 = new Intent(MainActivity.this,userActivity.class);
                  startActivity(t1);
                  psd.setText("");
                  user.setText("");
                  user.requestFocus();
              }
              else {
                  Toast.makeText(MainActivity.this,"账号或者密码错误！",Toast.LENGTH_SHORT).show();
                  psd.setText("");
                  user.setText("");
                  user.requestFocus();
              }
            }
        });

        TextView dlydwt = findViewById(R.id.dlydwt);
        dlydwt.setOnClickListener(new View.OnClickListener(){

        @Override

            public void onClick(View view) {
            dlydwt.setTextColor(Color.parseColor("#FFFF00"));
          //  Toast.makeText(MainActivity.this,"是否注册？",Toast.LENGTH_SHORT).show();
           // Intent it1 = new Intent(MainActivity.this,MainActivity3zhuc.class);
            //startActivity(it1);
            }

        });
        TextView zc = findViewById(R.id.zc);
        zc.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View view) {
                dlydwt.setTextColor(Color.parseColor("#FFFF00"));
                Toast.makeText(MainActivity.this,"开始注册",Toast.LENGTH_SHORT).show();
                //Intent it1 = new Intent(MainActivity.this,LoginActivity.class);
               // startActivity(it1);
            }

        });
    }

}