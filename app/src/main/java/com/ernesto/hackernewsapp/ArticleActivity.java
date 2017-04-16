package com.ernesto.hackernewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

public class ArticleActivity extends AppCompatActivity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Intent i = getIntent();
        if(i.hasExtra("url")) {
            url = i.getStringExtra("url");
            WebView webView = (WebView) findViewById(R.id.ArticleWebView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setInitialScale(100);
            webView.getSettings().setUseWideViewPort(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }
    }
}
