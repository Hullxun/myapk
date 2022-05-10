package com.example.myapk;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class SqliteInput  {
    /**
     * 向数据库写入数据
     */

    private MyDatabaseHelper dbHelper;
    private  String zc;  //正常出勤
    private  String zcjb;  //正常出勤加班
    private  String date; //正常出勤日期
    private  String time; //写入数据的时间

    public SqliteInput(String zc, String zcjb, String date, String time) {
        this.zc = zc;
        this.zcjb = zcjb;
        this.date = date;
        this.time = time;
    }

    public void setsql(){

        dbHelper.getWritableDatabase();
        // getWritableDatabase()会返回一个SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NormalAttendance", zc);  //正常出勤工时写入数据库
        values.put("NormalOvertime", zcjb);    //加班工时写入数据库
        values.put("Date",date);    //日期写入数据库
        values.put("time", time);
        //insert（）方法中第一个参数是表名，第二个参数是表示给表中未指定数据的自动赋值为NULL。第三个参数是一个ContentValues对象
        db.insert("Book", null, values);
        values.clear();
    }
    public void getsql(){
        //
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //指明去查询Book表。
        Cursor cursor = db.query("Book",null,null,null,null,null,null);
        //调用moveToFirst()将数据指针移动到第一行的位置。
        if (cursor.moveToFirst()){
            do {
                //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
                @SuppressLint("Range") String NormalAttendance = cursor.getString(cursor.getColumnIndex("NormalAttendance"));
                @SuppressLint("Range") String NormalOvertime = cursor.getString(cursor.getColumnIndex("NormalOvertime"));
                @SuppressLint("Range") String Dates = cursor.getString(cursor.getColumnIndex("Date"));
                @SuppressLint("Range") String times = cursor.getString(cursor.getColumnIndex("time"));
                /*double price = cursor.getDouble(cursor.getColumnIndex("price"));*/
                Log.d("MainPage","NormalAttendance："+NormalAttendance);
                Log.d("MainPage","NormalOvertime ："+NormalOvertime);
                Log.d("MainPage","Date ："+Dates);
                Log.d("MainPage","time："+times);


                //Toast.makeText(MainPage.this, name, Toast.LENGTH_SHORT).show();
            }while(cursor.moveToNext());

        }
        cursor.close();


        //
    }
}
