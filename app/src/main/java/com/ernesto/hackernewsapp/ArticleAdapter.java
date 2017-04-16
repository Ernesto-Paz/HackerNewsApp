package com.ernesto.hackernewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Gatobro on 4/14/17.
 */

public class ArticleAdapter extends ArrayAdapter<HackerNewsArticle> {
    private LayoutInflater myInflater;
    private Calendar myCalendar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

private static class ViewRecycler {
    TextView articleTitle;
    TextView articleScore;
    TextView articleDate;
    TextView articleNumber;

    public ViewRecycler(TextView articleTitle, TextView articleScore, TextView articleDate, TextView articleNumber) {
        this.articleTitle = articleTitle;
        this.articleScore = articleScore;
        this.articleDate = articleDate;
        this.articleNumber = articleNumber;
    }
}

    public ArticleAdapter(Context context, ArrayList<HackerNewsArticle> articles){
        super(context, R.layout.article_list_layout, articles);
        myCalendar = Calendar.getInstance();
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        HackerNewsArticle article = getItem(position); //get Object to covert into a view.
        //check  if if view is being recycled
        ViewRecycler viewRecycler;
        if(convertView == null) {
            //inflate view here since we're using a new one.
            convertView = myInflater.inflate(R.layout.article_list_layout, parent, false);
            TextView articleTitle = (TextView) convertView.findViewById(R.id.ArticleTitle);
            TextView articleScore = (TextView) convertView.findViewById(R.id.ArticleScore);
            TextView articleDate = (TextView) convertView.findViewById(R.id.ArticlePostDate);
            TextView articleNumber = (TextView) convertView.findViewById(R.id.ArticleNumber);
            viewRecycler = new ViewRecycler(articleTitle, articleScore, articleDate, articleNumber);
            convertView.setTag(viewRecycler);
        }
        else{
            viewRecycler = (ViewRecycler) convertView.getTag();
        }
        //plug in information into the inflated view from your HackerNewsArticle object.

        Log.i("Time", Long.toString(article.time * 1000));
        myCalendar.setTimeInMillis(article.time * 1000);//setting the date.
        Log.i("Calendar Time", Long.toString(myCalendar.getTimeInMillis()));
        String date = "Posted On: " + simpleDateFormat.format(myCalendar.getTime());
        viewRecycler.articleTitle.setText(article.title);
        //String literals need to be changes to android resources for ease of translation.
        viewRecycler.articleScore.setText("Score: " + Integer.toString(article.score));
        viewRecycler.articleDate.setText(date);
        viewRecycler.articleNumber.setText(Integer.toString(position + 1) + ".");

        return convertView;
    }


}
