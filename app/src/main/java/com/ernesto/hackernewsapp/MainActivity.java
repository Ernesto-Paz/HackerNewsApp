package com.ernesto.hackernewsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TopNewsIdsHandler, ArticleFromIdHandler {
    DownloadTopNewsIds<MainActivity> getHackerTopNews;
    ListView mainActivityNewsRoll;
    ArrayList<String> topNewsArray = new ArrayList<>(); //ArrayList of Article IDs
    ArrayList<String> ArticleInfo = new ArrayList<>();
    ArrayList<String> ArticleTitles = new ArrayList<>(); // ArrayList of Article Titles
    ArrayList<HackerNewsArticle> ArticleArray = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArticleAdapter articleAdapter;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityNewsRoll = (ListView) findViewById(R.id.mainactivitynewsroll);
        articleAdapter = new ArticleAdapter(this, ArticleArray);
        mainActivityNewsRoll.setAdapter(articleAdapter);
        getHackerTopNews = new DownloadTopNewsIds<>(this);
        getHackerTopNews.execute("https://hacker-news.firebaseio.com/v0/topstories.json");

        //get hackernews article info
        //parse news info
        //put info into a list and make an adapterview
        //save info into sql database if user requests it.
        //SQLiteDatabase articleIds = new SQLiteDatabase();
    }
    public void getArticleInfo(int NumOfArticles){

        int targetIndex = currentIndex + NumOfArticles;
        //Function will try to access up to targetIndex - 1 in the JSONArray.
        //Therefore if targetIndex is greater than the length of the array. It will reference a null index.
        if( targetIndex > topNewsArray.size() ){
            targetIndex = topNewsArray.size();
        }

        for(int i = currentIndex; i < targetIndex; i++){
                String ArticleCode = topNewsArray.get(i);
                new DownloadArticleFromId<>(this).execute("https://hacker-news.firebaseio.com/v0/item/" + ArticleCode + ".json");
                Log.i("ArticleCode", ArticleCode);
        }

        currentIndex = targetIndex;

    }

    @Override
    public void HandleTopNewsIds(String data) {
        Log.i("Info", data);
        try {
            //saves JSONArry to var so getArticleInfo() can access it late.
            JSONArray jsonArray = new JSONArray(data);
            //load JSONArry data into an ArrayList
            for(int i=0;i<jsonArray.length();i++){
                topNewsArray.add (jsonArray.getString(i));
            }
            getArticleInfo(20);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void HandleArticleFromId(String ArticleInfo){
        try{
            JSONObject Article = new JSONObject(ArticleInfo);
            HackerNewsArticle newArticle = new HackerNewsArticle(Article);
            if(newArticle.isValid == true) {
                ArticleArray.add(newArticle);
                articleAdapter.notifyDataSetChanged();
            }
        }
        catch(JSONException e){
            Log.e("JSON ERROR", "Unable to parse: " + ArticleInfo);
            e.printStackTrace();
        }



    }
}
