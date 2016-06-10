package com.stu.app.jyuapp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stu.app.jyuapp.R;

public class WebsiteContent extends AppCompatActivity {
    private WebView wv_website_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_content);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.i("20160609", "receiver url ::::" + url);
        wv_website_content = (WebView) findViewById(R.id.wv_website_content);
        wv_website_content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        wv_website_content.getSettings().setJavaScriptEnabled(true);
        wv_website_content.loadUrl(url);
    }
}
