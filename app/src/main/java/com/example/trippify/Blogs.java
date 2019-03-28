package com.example.trippify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Blogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);
    }

    public void blog1(View v)
    {
        Intent int1 =new Intent("com.example.trippify.BlogViewer");
        int1.putExtra("url","1");
        startActivity(int1);
    }
    public void blog2(View v)
    {
        Intent int1 =new Intent("com.example.trippify.BlogViewer");
        int1.putExtra("url","2");
        startActivity(int1);
    }
    public void blog3(View v)
    {
        Intent int1 =new Intent("com.example.trippify.BlogViewer");
        int1.putExtra("url","3");
        startActivity(int1);
    }
}
