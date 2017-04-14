package com.ernesto.hackernewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.zip.Inflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Gatobro on 4/14/17.
 */

public class ArticleAdapter extends ArrayAdapter<HackerNewsArticle> {
    private LayoutInflater inflater;

    public ArticleAdapter(Context context, ArrayList<HackerNewsArticle> articles){
        super(context, R.layout.article_list_layout, articles);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        HackerNewsArticle article = getItem(position); //get Object to covert into a view.
        //check  if if view is being recycled
        if(convertView == null){
            //inflate view here since we're using a new one.
            convertView = inflater.inflate(R.layout.article_list_layout, parent, false);
        }
        TextView articleTitle = (TextView) convertView.findViewById(R.id.ArticleTitle);
        TextView articleScore = (TextView) convertView.findViewById(R.id.ArticleScore);
        TextView articleDate = (TextView) convertView.findViewById(R.id.ArticlePostDate);
        //plug in information into the inflated view from your HackerNewsArticle object.

        articleTitle.setText(article.title);
        //String literals need to be changes to android resources for ease of translation.
        articleScore.setText("Score: " + Integer.toString(article.score));
        articleDate.setText("Date Posted: " + Integer.toString(article.time));

        return convertView;
    }


}
