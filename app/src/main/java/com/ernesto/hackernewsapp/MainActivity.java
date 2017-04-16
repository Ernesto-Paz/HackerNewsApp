package com.ernesto.hackernewsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
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
    ArrayList<HackerNewsArticle> articleArray = new ArrayList<>(); //Array of Article Information attaches to ArticleAdapter.
    SparseArray<HackerNewsArticle > articleSparseArray = new SparseArray<>(); //map which matches Articles to IDs
    ArrayAdapter<String> adapter;
    ArticleAdapter articleAdapter;
    int currentIndex;
    int articlesPlaced;
    int articlesLefttoGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentIndex = 0;
        articlesPlaced = 0;
        articlesLefttoGet = 0;
        mainActivityNewsRoll = (ListView) findViewById(R.id.mainactivitynewsroll);
        articleAdapter = new ArticleAdapter(this, articleArray);
        mainActivityNewsRoll.setAdapter(articleAdapter);
        getHackerTopNews = new DownloadTopNewsIds<>(this);
        getHackerTopNews.execute("https://hacker-news.firebaseio.com/v0/topstories.json");

        //get hackernews article info
        //parse news info
        //put info into a list and make an adapterview
        //save info into sql database if user requests it.
        //SQLiteDatabase articleIds = new SQLiteDatabase();
    }
    public void getArticleInfo(int NumOfArticlestoGet){
        //if the queue is empty, add more articles to the queue. Else, ignore additional requests.
        if(articlesLefttoGet == 0) {
            articlesLefttoGet = NumOfArticlestoGet;
        }
        else{
            NumOfArticlestoGet = 0;
        }
        int targetIndex = currentIndex + NumOfArticlestoGet;
        //Function will try to access up to targetIndex - 1 in the JSONArray.
        //Therefore if targetIndex is greater than the length of the array. It will reference a null index.
        if( targetIndex > topNewsArray.size() ){
            targetIndex = topNewsArray.size();
            articlesLefttoGet = topNewsArray.size() - currentIndex;
        }

        for(int i = currentIndex; i < targetIndex; i++){
                String ArticleCode = topNewsArray.get(i);
                new DownloadArticleFromId<>(this).execute("https://hacker-news.firebaseio.com/v0/item/" + ArticleCode + ".json");
        }

        currentIndex = targetIndex;

    }

    @Override
    public void HandleTopNewsIds(String data) {
        try {
            //saves JSONArry to var so getArticleInfo() can access it late.
            JSONArray jsonArray = new JSONArray(data);
            //load JSONArry data into an ArrayList
            for(int i=0;i<jsonArray.length();i++){
                topNewsArray.add (jsonArray.getString(i));
            }
            getArticleInfo(30);
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
            if(newArticle.isValid) {
                articlesLefttoGet--;
                articleSparseArray.put(newArticle.id, newArticle);
                if(articlesLefttoGet == 0 ) {
                    updateArticleArray();
                    articleAdapter.notifyDataSetChanged();
                }

            }
            else{
                articlesLefttoGet--;


            }
        }
        catch(JSONException e){
            articlesLefttoGet--;
            Log.e("JSON ERROR", "Unable to parse: " + ArticleInfo);
            e.printStackTrace();
        }



    }

    public void updateArticleArray(){
        //check each id in the Id array, inserting the article objects in order as it finds them.
        //when the size fo the articlelist is equal to the size of the sparsearray then all articles have been found and listed properly.

        while(articleSparseArray.size() > articleArray.size()){
            int articleid = Integer.parseInt(topNewsArray.get(articlesPlaced));
           HackerNewsArticle articletosort = articleSparseArray.get( articleid );
            if(articletosort != null) {
                articleAdapter.add(articletosort);
                articlesPlaced++;
            }
            else{
                articlesPlaced++;

            }
        }



    }
}
