package com.ernesto.hackernewsapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Gatobro on 4/11/17.
 */

public class DownloadTopNews<T extends Activity & DownloadHandler> extends DownloadFromUrlTask<T>{

    DownloadTopNews(T activityRef){
        super(activityRef);
    }

    @Override
    protected void onPostExecute(String s) {
        //passes String info back to the activity so it can handle the info.
        Log.i("Async", "Got Result.");
        activity.onDownloadTaskFinish(s);
    }
}
