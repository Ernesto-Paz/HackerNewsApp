package com.ernesto.hackernewsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements TopNewsIdsHandler, ArticleFromIdHandler {
    DownloadTopNewsIds<MainActivity> getHackerTopNews;
    JSONArray topNewsArray;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHackerTopNews = new DownloadTopNewsIds<>(this);
        getHackerTopNews.execute("https://hacker-news.firebaseio.com/v0/topstories.json");
        setContentView(R.layout.activity_main);
        //get hackernews article info
        //parse news info
        //put info into a list and make an adapterview
        //save info into sql database
        //SQLiteDatabase articleIds = new SQLiteDatabase();
    }
    public void getArticleInfo(int NumOfArticles){

        int targetIndex = currentIndex + NumOfArticles;
        //Function will try to access up to targetIndex - 1 in the JSONArray.
        //Therefore if targetIndex is greater than the length of the array. It will reference a null index.
        if( targetIndex > topNewsArray.length() ){
            targetIndex = topNewsArray.length();
        }

        for(int i = currentIndex; i < targetIndex; i++){
            try {
                String ArticleCode = topNewsArray.getString(i);
                new DownloadArticleFromId<>(this).execute("https://hacker-news.firebaseio.com/v0/item/" + ArticleCode + ".json");
                Log.i("ArticleCode", ArticleCode);
            }
            catch(JSONException e){
                //print JSON error. Print index that gave error.
                e.printStackTrace();
                Log.e("getArticleInfo", "JSON error getting index " + Integer.toString(i));
            }
        }

        currentIndex = targetIndex;

    }

    @Override
    public void HandleTopNewsIds(String data) {
        Log.i("Info", data);
        try {
            //saves JSONArry to var so getArticleInfo() can access it late.
            topNewsArray = new JSONArray(data);
            getArticleInfo(20);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void HandleArticleFromId(String ArticleInfo){
        /*{
          "by" : "dhouston",
          "descendants" : 71,
          "id" : 8863,
          "kids" : [ 8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901, 8902, 8907, 8894, 8878, 8870, 8980, 8934, 8876 ],
          "score" : 111,
          "time" : 1175714200,
          "title" : "My YC app: Dropbox - Throw away your USB drive",
          "type" : "story",
          "url" : "http://www.getdropbox.com/u/2/screencast.html"
            }*/
        try{
            JSONObject Article = new JSONObject(ArticleInfo);
            String ArticleUrl = Article.getString("url");
            String ArticleTitle = Article.getString("title");
            Log.i("Title", ArticleTitle);
            Log.i("URL", ArticleUrl);

        }
        catch(JSONException e){
            Log.e("JSON ERROR", "Unable to parse: " + ArticleInfo);
            e.printStackTrace();
        }



    }
}
