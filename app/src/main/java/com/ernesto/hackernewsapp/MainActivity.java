package com.ernesto.hackernewsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements DownloadHandler {
    DownloadTopNews<MainActivity> getHackerTopNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHackerTopNews = new DownloadTopNews<>(MainActivity.this);
        getHackerTopNews.execute("https://hacker-news.firebaseio.com/v0/topstories.json");
        setContentView(R.layout.activity_main);
        //get hackernews article info
        //parse news info
        //put info into a list and make an adapterview
        //save info into sql database
        //SQLiteDatabase articleIds = new SQLiteDatabase();
    }

    @Override
    public void onDownloadTaskFinish(String data) {
        Log.i("Info", data);
        try {
            JSONArray TopNewsArray = new JSONArray(data);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }
}
