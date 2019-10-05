package com.example.motivational.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.motivational.R;

public class instagram extends AppCompatActivity {
    private WebView instagram_view;
    private ProgressBar insta_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);

        insta_progress=findViewById(R.id.insta_progress);
        insta_progress.setVisibility(View.VISIBLE);

        instagram_view=findViewById(R.id.instagram_web);
        instagram_view.setWebViewClient(new WebViewClient());
        instagram_view.loadUrl("https://www.instagram.com/mymarketmind/");
        WebSettings webSettings=instagram_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        insta_progress.setVisibility(View.GONE);
     }

    @Override
    public void onBackPressed() {
       if(instagram_view.canGoBack())
       {
           instagram_view.goBack();
       }
       else
       {
           super.onBackPressed();
       }
    }
}
