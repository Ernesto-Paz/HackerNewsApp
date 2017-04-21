package com.ernesto.hackernewsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Gatobro on 4/21/17.
 */


//Custom RecyclerAdapter inherits from the RecyclerView.Adapter which takes ArticleAdapterRecyler.ViewHolder as a type parameter.
public class ArticleAdapterRecycler<T extends MainActivity> extends RecyclerView.Adapter<ArticleAdapterRecycler.ViewHolder> {
    ArrayList<HackerNewsArticle> dataset;
    Calendar myCalendar = Calendar.getInstance();
    T context;

    ArticleAdapterRecycler(ArrayList<HackerNewsArticle> data, T activity){
        dataset = data;
        context = activity;
    }
    public abstract class MyOnClickListener implements View.OnClickListener{
        HackerNewsArticle article;
        MyOnClickListener(HackerNewsArticle a) {
            article = a;
        }
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

    //custom ViewHolder must inherit from RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView articleTitle;
        TextView articleScore;
        TextView articleDate;
        TextView articleNumber;
        ImageButton articleGoToContentButton;

        public ViewHolder(View itemView){
            super(itemView);
            this.articleTitle = (TextView) itemView.findViewById(R.id.ArticleTitle);
            this.articleScore = (TextView) itemView.findViewById(R.id.ArticleScore);
            this.articleDate = (TextView) itemView.findViewById(R.id.ArticlePostDate);
            this.articleNumber = (TextView) itemView.findViewById(R.id.ArticleNumber);
            this.articleGoToContentButton = (ImageButton) itemView.findViewById(R.id.GoToArticleContentButton);
        }

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position >(dataset.size() - 5)){
            MainActivity activity = context;
            if(activity.articlesLeftToGet == 0) {
                activity.getArticleInfo(1);
            }
        }

        HackerNewsArticle article = dataset.get(position);
        holder.articleTitle.setText(article.title);
        holder.articleDate.setText(getTimeDifference(article.time));
        holder.articleNumber.setText(Integer.toString(position + 1) + ".");
        holder.articleScore.setText("Score: " + Integer.toString(article.score));
        if(article.url != "") {
            holder.articleGoToContentButton.setOnClickListener(new MyOnClickListener(article) {

                @Override
                public void onClick(View v) {
                    //HackerNewsArticle article = (HackerNewsArticle) v.getTag();//have url.
                    Intent i = new Intent(context.getApplicationContext(), ArticleActivity.class);
                    i.putExtra("url", article.url);
                    context.startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    //creating new ViewHolder.
    @Override
    public ArticleAdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_layout, parent, false);
        ArticleAdapterRecycler.ViewHolder viewHolder = new ArticleAdapterRecycler.ViewHolder(v);
        return viewHolder;
    }
}
