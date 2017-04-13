package com.ernesto.hackernewsapp;

/**
 * Created by Gatobro on 4/13/17.
 */

import android.app.Activity;

public class DownloadArticleFromId<T extends Activity & ArticleFromIdHandler> extends DownloadFromUrlTask<T> {

    DownloadArticleFromId(T activityref){
        super(activityref);
    }

    @Override
    protected void onPostExecute(String s) {
        //pass info to Activity for handling/storage.
        activity.HandleArticleFromId(s);
    }
}
