package com.ernesto.hackernewsapp;

/**
 * Created by Gatobro on 4/13/17.
 */

import android.app.Activity;

import java.util.ArrayList;

public class DownloadArticleFromId<T extends Activity & ArticleFromIdHandler> extends DownloadFromUrlTask<T> {
    static ArrayList<DownloadArticleFromId> allTasks = new ArrayList<>();

    DownloadArticleFromId(T activityref){
        super(activityref);
        allTasks.add(this);
    }

    public static void cancelAllTasks(){
        for(int i = 0; i< allTasks.size();i++){
            allTasks.get(i).cancel(true);
        }
        allTasks.clear();
    }

    @Override
    protected void onPostExecute(String s) {
        //pass info to Activity for handling/storage.
        int index = allTasks.indexOf(this);
        allTasks.remove(index);
        allTasks.trimToSize();
        activity.HandleArticleFromId(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }
}
