package com.example.myapk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class MyDAO {
    private SQLiteDatabase myDb;
    private DbHelper dbHelper;
   /** private  String zc;  //正常出勤
    private  String zcjb;  //正常出勤加班
    private  String date; //正常出勤日期
    private  String time; //写入数据的时间
*/

    public MyDAO(Context context){
        dbHelper=new DbHelper(context,"Book.db",null,1);
    }
    public Cursor allQuery(){  //获取所有记录
        myDb=dbHelper.getReadableDatabase();
        return  myDb.rawQuery("select * from Book",null);
    }
    public Cursor Query(String selind){  //查询指定的记录
        //SELECT * FROM "Book" WHERE Date = '2022-1-14'
        //SELECT * FROM "Book" WHERE Date='2022-1-14'
        myDb=dbHelper.getReadableDatabase();
       String[] whereArgs = {selind};

        return  myDb.rawQuery("select * from Book WHERE Date=?",whereArgs);
    }
    public int getRecordsNumber(){  //获取所有记录数量
        myDb=dbHelper.getReadableDatabase();
        Cursor cursor=myDb.rawQuery("select * from Book",null);
        myDb.close();
        return cursor.getCount();
    }
    public void insertInfo(String zc,String zcjb,String date,String time){  //插入记录
        myDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NormalAttendance", zc);  //正常出勤工时写入数据库
        values.put("NormalOvertime", zcjb);    //加班工时写入数据库
        values.put("Date",date);    //日期写入数据库
        values.put("time", time);
        long rowid=myDb.insert(DbHelper.TB_NAME, null, values);
        if(rowid==-1)

            Log.i("myDbDemo", "数据插入失败！");
        else
            Log.i("myDbDemo", "数据插入成功！"+rowid+"条");
        myDb.close();
    }
    public boolean deleteInfo(String selId){  //删除记录
        String where = "Date=?" ;
        String[] whereArgs = {selId};
        int i = myDb.delete(DbHelper.TB_NAME, where, whereArgs);
        if (i > 0){
            Log.i("myDbDemo", "数据删除成功！");
            myDb.close();
            return true;
        }


        else{
            Log.i("myDbDemo", "数据未删除！");
            myDb.close();
            return false;
        }



    }
    public void updateInfo(String zc,String zcjb,String date,String time){  //修改记录
        //方法中的第三参数用于修改选定的记录
        ContentValues values = new ContentValues();
        values.put("NormalAttendance", zc);  //正常出勤工时写入数据库
        values.put("NormalOvertime", zcjb);    //加班工时写入数据库
        values.put("time", time);
        String where="Date=?";
        String[] whereArgs = { date};
        int i=myDb.update(DbHelper.TB_NAME, values, where, whereArgs);

        //上面几行代码的功能可以用下面的一行代码实现
        //myDb.execSQL("update friends set name = ? ,age = ? where _id = ?",new Object[]{name,age,selId});

        if(i>0)
            Log.i("myDbDemo","数据更新成功！");
        else
            Log.i("myDbDemo","数据未更新！");
        myDb.close();
    }
    //判断是否打开
    public boolean isopen(){
        myDb = dbHelper.getWritableDatabase();
        if(myDb.isOpen()){
            return true;
        }
        else {
            return false;
        }
    }
}

