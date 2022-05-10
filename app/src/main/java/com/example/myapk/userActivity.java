package com.example.myapk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class userActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        TextView textView_gsjl=findViewById(R.id.tvbar_gsjl);
        textView_gsjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t1 = new Intent(userActivity.this,MainActivity2.class);
                startActivity(t1);
            }
        });
        TextView textView_tvbar_tj=findViewById(R.id.tvbar_tj);
        textView_tvbar_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t1 = new Intent(userActivity.this,MainActivity3.class);
                startActivity(t1);
            }
        });
    }
}