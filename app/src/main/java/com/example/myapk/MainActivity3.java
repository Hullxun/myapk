package com.example.myapk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        MyDAO myDAO = new MyDAO(MainActivity3.this);
        //获取控件
        DatePicker datePicker =findViewById(R.id.data);
        TextView textView1 = findViewById(R.id.operationUi_text1);
        TextView textView2 = findViewById(R.id.operationUi_text2);
        Button b1 = findViewById(R.id.operationUi_button);
        //获取当前设备日期
        Calendar c1=Calendar.getInstance();
        int year =  c1.get(Calendar.YEAR);
        int month =c1.get(Calendar.MONTH);
        int day =c1.get(Calendar.DATE);
        String strdatet=(year + "-" + month+ "-" + day);
        Log.d("operationUi","strdatet："+strdatet);
        //日历显示
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener(){

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取用户当前选择的日期
                textView1.setText("");
                textView2.setText("");
                int years =  datePicker.getYear();
                int months =  datePicker.getMonth()+1;
                int days =  datePicker.getDayOfMonth();
                String strdate=(years + "-" + months + "-" + days);
                Log.d("operationUi","strdate："+strdate);
                Cursor cursor =  myDAO.Query(strdate);
                if(cursor.getCount()>0){
                    if (cursor.moveToFirst()){
                        do {
                            //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
                            @SuppressLint("Range") int name = cursor.getInt(cursor.getColumnIndex("NormalAttendance"));
                            @SuppressLint("Range") int author = cursor.getInt(cursor.getColumnIndex("NormalOvertime"));
                            @SuppressLint("Range") String dates = cursor.getString(cursor.getColumnIndex("Date"));
                            @SuppressLint("Range") String pages = cursor.getString(cursor.getColumnIndex("time"));
                            ///*double price = cursor.getDouble(cursor.getColumnIndex("price"));*//*
                            Log.d("operationUi","NormalAttendance："+name);
                            Log.d("operationUi","NormalOvertime ："+author);
                            Log.d("operationUi","Date ："+dates);
                            Log.d("operationUi","time："+pages);
                            String str="正常出勤："+name+"小时\n"+"正常加班："+author +"小时\n"+"日期："+dates;
                            String str2="正常出勤："+name*11+"元\n"+"正常加班："+author*19 +"元\n"+"日期："+dates;
                            textView1.setText(str);
                            textView2.setText(str2);

                            //Toast.makeText(MainPage.this, name, Toast.LENGTH_SHORT).show();
                        }while(cursor.moveToNext());

                    }
                }
                else {
                    textView1.setText("该日期还没有记录工时呢！赶紧去添加吧！");
                }
                cursor.close();
            }
        });
        TextView textView_gsjl=findViewById(R.id.tvbar_gsjl);
        textView_gsjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t1 = new Intent(MainActivity3.this,MainActivity2.class);
                startActivity(t1);
            }
        });
    }
}