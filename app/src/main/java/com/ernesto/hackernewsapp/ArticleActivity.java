package com.ernesto.hackernewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;

public class ArticleActivity extends AppCompatActivity {
    String url;
    WebView webView;
    WebViewClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ArticleActivityToolbar);
        setSupportActionBar(myToolbar);
        Intent i = getIntent();
        if(i.hasExtra("url")) {
            url = i.getStringExtra("url");
            webView = (WebView) findViewById(R.id.ArticleWebView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setInitialScale(100);
            webView.getSettings().setUseWideViewPort(true);
            client = new WebViewClient();
            webView.setWebViewClient(client);
            try{
            webView.loadUrl(url);
            }
            catch(Exception e){
                Log.e("WebView", "Url failed to load.");
                e.printStackTrace();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case(R.id.action_refresh):
                Log.i("Refresh", "Refreshing page.");
                webView.loadUrl(url);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
