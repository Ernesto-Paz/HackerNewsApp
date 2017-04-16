package com.ernesto.hackernewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;

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
    ImageButton articleGoToContentButton;

    public ViewRecycler(TextView articleTitle, TextView articleScore, TextView articleDate, TextView articleNumber, ImageButton articleGoToContentButton) {
        this.articleTitle = articleTitle;
        this.articleScore = articleScore;
        this.articleDate = articleDate;
        this.articleNumber = articleNumber;
        this.articleGoToContentButton = articleGoToContentButton;
    }
}

    public ArticleAdapter(Context context, ArrayList<HackerNewsArticle> articles){
        super(context, R.layout.article_list_layout, articles);
        TimeZone zone = TimeZone.getTimeZone("etc/UTC");
        myCalendar = Calendar.getInstance(zone);
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private String getTimeDifference(long articletime){
        long currentTime = myCalendar.getTimeInMillis();
        String date = "Posted ";
        long timeDifference = (currentTime - articletime)/1000; //difference in seconds.
        if(timeDifference < 60){
            date = date + Long.toString(timeDifference) + " seconds ago.";
            return date;
        }
        timeDifference /= 60; //difference in minutes;
        if(timeDifference < 60){
            date = date + Long.toString(timeDifference) + " minutes ago.";
            return date;
        }
        timeDifference /= 60; //difference in hours;
        if(timeDifference < 24){
            date = date + Long.toString(timeDifference) + " hours ago.";
            return date;
        }
        timeDifference /= 24; //difference in days;
        if(timeDifference < 30){
            date = date + Long.toString(timeDifference) + " days ago.";
            return date;
        }
        timeDifference /= 30; //difference in months;
        if(timeDifference < 12){
            date = date + Long.toString(timeDifference) + " months ago.";
            return date;
        }
        timeDifference /= 12; //difference in years;
        date = date + Long.toString(timeDifference) + " years ago.";
        return date;
    }


    @Override @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        HackerNewsArticle article = getItem(position); //get Object to covert into a view.
        if(position >(getCount() - 15)){
            MainActivity activity = (MainActivity) getContext();
            if(activity.articlesLefttoGet == 0) {
                activity.getArticleInfo(1);
            }
        }
        //check  if if view is being recycled
        ViewRecycler viewRecycler;
        if(convertView == null) {
            //inflate view here since we're using a new one.
            convertView = myInflater.inflate(R.layout.article_list_layout, parent, false);
            TextView articleTitle = (TextView) convertView.findViewById(R.id.ArticleTitle);
            TextView articleScore = (TextView) convertView.findViewById(R.id.ArticleScore);
            TextView articleDate = (TextView) convertView.findViewById(R.id.ArticlePostDate);
            TextView articleNumber = (TextView) convertView.findViewById(R.id.ArticleNumber);
            ImageButton articleGoToContentButton = (ImageButton) convertView.findViewById(R.id.GoToArticleContentButton);
            viewRecycler = new ViewRecycler(articleTitle, articleScore, articleDate, articleNumber, articleGoToContentButton);
            convertView.setTag(viewRecycler);
        }
        else{
            viewRecycler = (ViewRecycler) convertView.getTag();
        }
        viewRecycler.articleGoToContentButton.setTag(article);
        viewRecycler.articleGoToContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HackerNewsArticle article = (HackerNewsArticle) v.getTag();//have url.
                Intent i = new Intent(getContext().getApplicationContext(), ArticleActivity.class);
                i.putExtra("url", article.url);
                getContext().startActivity(i);
            }
        });

        //plug in information into the inflated view from your HackerNewsArticle object.
        String date = getTimeDifference(article.time);
        viewRecycler.articleTitle.setText(article.title);
        //String literals need to be changes to android resources for ease of translation.
        viewRecycler.articleScore.setText("Score: " + Integer.toString(article.score));
        viewRecycler.articleDate.setText(date);
        viewRecycler.articleNumber.setText(Integer.toString(position + 1) + ".");

        return convertView;
    }


}
