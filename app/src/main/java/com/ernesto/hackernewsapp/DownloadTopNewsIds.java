package com.ernesto.hackernewsapp;

import android.app.Activity;
import android.util.Log;

/**
 * Created by Gatobro on 4/11/17.
 */

public class DownloadTopNewsIds<T extends Activity & TopNewsIdsHandler> extends DownloadFromUrlTask<T>{

    DownloadTopNewsIds(T activityRef){
        super(activityRef);
    }

    @Override
    protected void onPostExecute(String s) {
        //passes String info back to the activity so it can handle the info.
        Log.i("Async", "Got Result.");
        activity.HandleTopNewsIds(s);
    }
}
