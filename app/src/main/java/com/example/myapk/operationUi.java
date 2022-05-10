package com.example.myapk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class operationUi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_ui);
        //实例化数据库
       MyDAO myDAO = new MyDAO(operationUi.this);
        //获取控件

        DatePicker datePicker =findViewById(R.id.data);

        TextView textView1 = findViewById(R.id.operationUi_text1);
        Button b1 = findViewById(R.id.operationUi_button);

        //获取当前设备日期
        Calendar c1=Calendar.getInstance();
        int year =  c1.get(Calendar.YEAR);
        int month =c1.get(Calendar.MONTH);
        int day =c1.get(Calendar.DATE);
        String strdatet=(year + "-" + month+ "-" + day);
        Log.d("operationUi","strdatet："+strdatet);
       //datePicker.updateDate(year,month,day);
        //为日历日期设置监听器，当用户选的日期发生变更时触发
     // datePicker.setOnDateChangedListener();

       datePicker.init(year,month,day,new DatePicker.OnDateChangedListener(){

           @Override
           public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
               //获取用户当前选择的日期
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
                           @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("NormalAttendance"));
                           @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("NormalOvertime"));
                           @SuppressLint("Range") String dates = cursor.getString(cursor.getColumnIndex("Date"));
                           @SuppressLint("Range") String pages = cursor.getString(cursor.getColumnIndex("time"));
                           ///*double price = cursor.getDouble(cursor.getColumnIndex("price"));*//*
                           Log.d("operationUi","NormalAttendance："+name);
                           Log.d("operationUi","NormalOvertime ："+author);
                           Log.d("operationUi","Date ："+dates);
                           Log.d("operationUi","time："+pages);
                           String str="正常出勤："+name+"小时\n"+"正常加班："+author +"小时\n"+"日期："+dates;
                           textView1.setText(str);

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



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int years =  datePicker.getYear();
                int months =  datePicker.getMonth()+1;
                int days =  datePicker.getDayOfMonth();
                String strdate=(years + "-" + months + "-" + days);
                Cursor cursor= myDAO.Query(strdate);
                int cur_length=  cursor.getCount();

                if(cur_length==0){
                    Toast.makeText(operationUi.this,"查询数量为："+cur_length+"执行插入语句",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(operationUi.this,"查询数量为："+cur_length+"执行修改语句",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}