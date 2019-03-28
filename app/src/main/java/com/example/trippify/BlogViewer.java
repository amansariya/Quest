package com.example.trippify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class BlogViewer extends AppCompatActivity {

    String urlview;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_viewer);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        int x = Integer.parseInt(url);
        webView = (WebView) findViewById(R.id.lolol);
        switch (x) {
            case 1:
                blog1();
                break;
            case 2:
                blog2();
                break;
            case 3:
                blog3();
                break;
            default:
                break;


        }
    }

    public void blog1() {


        urlview = "";

        urlview = "https://firebasestorage.googleapis.com/v0/b/trippify-e3149.appspot.com/o/Blog%204.html?alt=media&token=975a4d99-21d4-4b7e-bcc4-c65d35506f67";
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(urlview);

    }

    public void blog2() {
        urlview = "";

        urlview = "https://firebasestorage.googleapis.com/v0/b/trippify-e3149.appspot.com/o/Blog%206.html?alt=media&token=3c4d7c85-3fa3-4b75-b44c-4cb921521738";
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(urlview);

    }

    public void blog3() {
        urlview = "";

        urlview = "https://firebasestorage.googleapis.com/v0/b/trippify-e3149.appspot.com/o/Blog%207.html?alt=media&token=34ff2fff-7814-42cf-88bb-bd1f3383860a";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(urlview);
    }

}
