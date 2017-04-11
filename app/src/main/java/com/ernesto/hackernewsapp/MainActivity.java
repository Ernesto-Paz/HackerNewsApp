package com.ernesto.hackernewsapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get hackernews article info
        //parse news info
        //put info into a list and make an adapterview
        //save info into sql database
        SQLiteDatabase articleIds = new SQLiteDatabase();

    }
}
