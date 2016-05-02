package com.nicatec.twitceratops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.model.TweetDAO;
import com.nicatec.twitceratops.model.Tweets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //al arrancar comienzo con lo qu haya en la BD
        TweetDAO tweetDAO = new TweetDAO(getApplicationContext());
        Tweets tweets = tweetDAO.query();


    }
}
