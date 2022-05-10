package com.example.myapk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;



// NormalAttendance 正常出勤工时   NormalOvertime 加班工时
public class MainPage extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Button b1 = findViewById(R.id.startinter);
        EditText zc_cq = findViewById(R.id.zc_cq);  //获取正常出勤的值
        EditText jb_cq = findViewById(R.id.jb_cq);

        Button b2 = findViewById(R.id.startinter2);  //查询按钮
        Button bceci = findViewById(R.id.startinter3);
        DatePicker datePicker = findViewById(R.id.data);

        //获取当前时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
         String datetime =   df.format(new Date()); //实时插入数据时间
        //  获取年月日

        //
        MyDAO myDAO = new MyDAO(MainPage.this);
       b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (zc_cq.getText().toString().length() > 0 && jb_cq.getText().toString().length() > 0) {
                     int zc_cq_int = Integer.parseInt(zc_cq.getText().toString());
                     int jb_cq_int = Integer.parseInt(jb_cq.getText().toString());
                     if (zc_cq_int <= 8 && zc_cq_int > 0 && jb_cq_int > 0 && jb_cq_int <= 24) {
                         int year =  datePicker.getYear();
                         int month =  datePicker.getMonth()+1;
                         int day =  datePicker.getDayOfMonth();
                       /*  dbHelper.getWritableDatabase();
                         // getWritableDatabase()会返回一个SQLiteDatabase对象
                         SQLiteDatabase db = dbHelper.getWritableDatabase();
                         ContentValues values = new ContentValues();*/
                         //


                         String strdate=(year + "-" + month + "-" + day);

                         //实例化sqlite
                         try {

                             myDAO.insertInfo( zc_cq.getText().toString(), jb_cq.getText().toString(),strdate,datetime);//获取全部数据

                         }catch (Exception ex){
                             Toast.makeText(MainPage.this, ex.toString(), Toast.LENGTH_SHORT).show();
                         }


                     } else {
                         Toast.makeText(MainPage.this, "请正确输入工时", Toast.LENGTH_SHORT).show();
                     }
                 }
             }
         }
       );


        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                try {

                    Cursor cursor =myDAO.allQuery();//获取全部数据
                    if (cursor.moveToFirst()){
                        do {
                            //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
                            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("NormalAttendance"));
                            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("NormalOvertime"));
                            @SuppressLint("Range") String dates = cursor.getString(cursor.getColumnIndex("Date"));
                            @SuppressLint("Range") String pages = cursor.getString(cursor.getColumnIndex("time"));
                            ///*double price = cursor.getDouble(cursor.getColumnIndex("price"));*//*
                            Log.d("MainPage","NormalAttendance："+name);
                            Log.d("MainPage","NormalOvertime ："+author);
                            Log.d("MainPage","Date ："+dates);
                            Log.d("MainPage","time："+pages);


                            //Toast.makeText(MainPage.this, name, Toast.LENGTH_SHORT).show();
                        }while(cursor.moveToNext());

                    }
                    cursor.close();


                }catch (Exception ex){
                    Toast.makeText(MainPage.this, ex.toString(), Toast.LENGTH_SHORT).show();
                }
                //SQLiteDatabase db = dbHelper.getWritableDatabase();
                //指明去查询Book表。
              //  Cursor cursor = db.query("Book",null,null,null,null,null,null);

                //调用moveToFirst()将数据指针移动到第一行的位置。




            }
        });


        bceci.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
               /* String zc_cqes = (zc_cq.getText().toString());
                String jb_cqes = (jb_cq.getText().toString());
                int year =  datePicker.getYear();
                int month =  datePicker.getMonth()+1;
                int day =  datePicker.getDayOfMonth();
                String strdate=(year + "-" + month + "-" + day);
              //  myDAO.deleteInfo("2022-1-13");  //指定字段删除值
                myDAO.updateInfo(zc_cqes,jb_cqes,datetime,strdate);  //修改值
                // int ppy=  myDAO. getRecordsNumber();
                //Log.d("MainPage","删除成功！");*/
                Intent t1 = new Intent(MainPage.this,operationUi.class);
                startActivity(t1);
            }
        });

    }
}