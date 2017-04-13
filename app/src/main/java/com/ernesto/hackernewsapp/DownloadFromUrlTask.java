package com.ernesto.hackernewsapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Gatobro on 4/11/17.
 */

public abstract class DownloadFromUrlTask<T extends Activity & DownloadHandler> extends AsyncTask<String, Void, String> {

    T activity;

    DownloadFromUrlTask(T activityRef){
        activity = activityRef;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        HttpURLConnection http = null;
        Log.i("Async","Fetching information.");
        try {
            URL url = new URL(urls[0]); // making a new URL
            http = (HttpURLConnection) url.openConnection(); //opening a connection using URL.
            InputStream input = http.getInputStream(); // getting the input stream
            InputStreamReader reader = new InputStreamReader(input); //making a reader..
            int data  = reader.read();
            while(data != -1){
                result += (char)data;
                data = reader.read();
            }
        }
        catch(IOException e){
            Log.e("Error Malformed URL", urls[0]);
            e.printStackTrace();
            return result;
        }

        return result;

    }

    @Override
    protected abstract void onPostExecute(String s);
}
