package com.ernesto.hackernewsapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gatobro on 4/14/17.
 */
/*
        {
          "by" : "dhouston",
          "descendants" : 71,
          "id" : 8863,
          "kids" : [ 8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901, 8902, 8907, 8894, 8878, 8870, 8980, 8934, 8876 ],
          "score" : 111,
          "time" : 1175714200,
          "title" : "My YC app: Dropbox - Throw away your USB drive",
          "type" : "story",
          "url" : "http://www.getdropbox.com/u/2/screencast.html"
        }
*/
public class HackerNewsArticle {
    boolean isValid = true;
    String title;
    Integer time;
    String url;
    Integer score;
    HackerNewsArticle(JSONObject jsonObject){
        try {
            title = jsonObject.getString("title");
            time = jsonObject.getInt("time");
            url = jsonObject.getString("url");
            score = jsonObject.getInt("score");
        }
        catch(JSONException e){
            isValid = false;
            e.printStackTrace();
        }


    }


}