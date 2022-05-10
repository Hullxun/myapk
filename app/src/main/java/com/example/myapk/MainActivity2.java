package com.example.myapk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/*public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        WebView webView = (WebView) findViewById(R.id.webview);

        //如果只写下面这一句，会提示无法访问
            //加载网页需要连接互联网的权限，需要在AndroidManifest中进行声明
             //这样的好处：可以让用户很清楚的看到app所需要的权限
              //用户并不清楚和重视权限问题，造成了病毒问题的泛滥。其实是可以避免的！

        webView.loadUrl("http://www.dxzy163.com/");

        //
    }
}*/
//**
public class MainActivity2 extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         // /按钮
        Button star = findViewById(R.id.startinter);
        EditText zc_cq = findViewById(R.id.zc_cq);  //获取正常出勤的值
        EditText jb_cq = findViewById(R.id.jb_cq);
        //
        Button delete = findViewById(R.id.delete);  //删除记录按钮
        DatePicker datePicker = findViewById(R.id.data);

        //获取当前时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String datetime =   df.format(new Date()); //实时插入数据时间
        //  获取年月日
        //

        //添加记录
        star.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      //判断是否有数据
                                      MyDAO myDAO = new MyDAO(MainActivity2.this);
                                      if(myDAO.isopen()){
                                          int years =  datePicker.getYear();
                                          int months =  datePicker.getMonth()+1;
                                          int days =  datePicker.getDayOfMonth();
                                          String strdate=(years + "-" + months + "-" + days);
                                          Cursor cursor= myDAO.Query(strdate);
                                          int cur_length=  cursor.getCount();
                                          if(cur_length==0){
                                              // Toast.makeText(MainActivity2.this,"查询数量为："+cur_length+"执行插入语句",Toast.LENGTH_SHORT).show();
                                              Log.d("MainActivity2","添加记录：cur_length："+cur_length);
                                              if (zc_cq.getText().toString().length() > 0 && jb_cq.getText().toString().length() > 0) {
                                                  int zc_cq_int = Integer.parseInt(zc_cq.getText().toString());
                                                  int jb_cq_int = Integer.parseInt(jb_cq.getText().toString());
                                                  if (zc_cq_int <= 8 && zc_cq_int > 0 && jb_cq_int > 0 && jb_cq_int <= 24) {
                                                      //实例化sqlite
                                                      try {

                                                          myDAO.insertInfo( zc_cq.getText().toString(), jb_cq.getText().toString(),strdate,datetime);//插入数据
                                                          Cursor cursors= myDAO.Query(strdate);
                                                          int cur_lengths=  cursors.getCount();
                                                          if(cur_lengths>0){
                                                              Toast.makeText(MainActivity2.this,"添加成功！", Toast.LENGTH_SHORT).show();
                                                          }
                                                          else {
                                                              Toast.makeText(MainActivity2.this,"添加失败！", Toast.LENGTH_SHORT).show();
                                                          }

                                                      }catch (Exception ex){
                                                          Toast.makeText(MainActivity2.this, ex.toString(), Toast.LENGTH_SHORT).show();
                                                      }


                                                  } else {
                                                      Toast.makeText(MainActivity2.this, "请正确输入工时", Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          }
                                          else {
                                              Log.d("MainActivity2","  修改记录：cur_length："+cur_length);
                                              //Toast.makeText(MainActivity2.this,"查询数量为："+cur_length+"执行修改语句",Toast.LENGTH_SHORT).show();
                                              if (zc_cq.getText().toString().length() > 0 && jb_cq.getText().toString().length() > 0) {
                                                  int zc_cq_int = Integer.parseInt(zc_cq.getText().toString());
                                                  int jb_cq_int = Integer.parseInt(jb_cq.getText().toString());
                                                  if (zc_cq_int <= 8 && zc_cq_int > 0 && jb_cq_int > 0 && jb_cq_int <= 24) {
                                                      //实例化sqlite
                                                      try {

                                                          myDAO.updateInfo( zc_cq.getText().toString(), jb_cq.getText().toString(),strdate,datetime);//获取全部数据

                                                      }catch (Exception ex){
                                                          Toast.makeText(MainActivity2.this, ex.toString(), Toast.LENGTH_SHORT).show();
                                                      }


                                                  } else {
                                                      Toast.makeText(MainActivity2.this, "请正确输入工时", Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          }
                                      }


                                  }
                              }
        );

          //删除记录
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MyDAO myDAO = new MyDAO(MainActivity2.this);
                try {
                   if(myDAO.isopen()) {
                       int year =  datePicker.getYear();
                       int month =  datePicker.getMonth()+1;
                       int day =  datePicker.getDayOfMonth();
                       String strdate=(year + "-" + month + "-" + day);
                       Log.d("MainPage","strdate:  "+strdate);
                    boolean trs =  myDAO.deleteInfo(strdate);  //指定字段删除值
                       // int ppy=  myDAO. getRecordsNumber();
                       if(trs){
                           Toast.makeText(MainActivity2.this, "数据删除成功！", Toast.LENGTH_SHORT).show();

                       }
                       else {
                           Toast.makeText(MainActivity2.this, "数据失败，可能没有添加数据！", Toast.LENGTH_SHORT).show();
                       }

                   }



                }catch (Exception ex){
                    Toast.makeText(MainActivity2.this, ex.toString(), Toast.LENGTH_SHORT).show();
                }
             // SQLiteDatabase db = dbHelper.getWritableDatabase();
                //指明去查询Book表。
                //  Cursor cursor = db.query("Book",null,null,null,null,null,null);

                //调用moveToFirst()将数据指针移动到第一行的位置。




            }
        });

        /*
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
                //Log.d("MainPage","删除成功！");
                Intent t1 = new Intent(MainActivity2.this,operationUi.class);
                startActivity(t1);
            }
        });*/

        //
        TextView textView_tvbar_tj=findViewById(R.id.tvbar_tj);
        textView_tvbar_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t1 = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(t1);
            }
        });
    }
}