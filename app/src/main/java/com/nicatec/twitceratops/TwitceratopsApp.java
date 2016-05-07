package com.nicatec.twitceratops;

import android.app.Application;

import com.nicatec.twitceratops.model.DBConstants;
import com.nicatec.twitceratops.model.DBHelper;

/**
 * Created by vtx on 2/5/16.
 */
public class TwitceratopsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //configutro la BD
        DBHelper.configure(DBConstants.DBNAME, getApplicationContext());


/*
        TweetDAO tweetDAO = new TweetDAO(getApplicationContext());
        TweetMessage m = new TweetMessage(0,"hola","kkkkk",-3.55274f,40.1243592f);
        TweetMessage m1 = new TweetMessage(0,"adios","kkkkk",-3.55200f,40.1243882f);
        TweetMessage m2 = new TweetMessage(0,"cucu","kkkkk",-3.55302f,40.1243108f);
        TweetMessage m3 = new TweetMessage(0,"trastras","kkkkk",-3.55780f,40.1243490f);
        TweetMessage m4 = new TweetMessage(0,"saltaba","kkkkk",-3.55007f,40.12433780f);
        TweetMessage m5 = new TweetMessage(0,"la rana","kkkkk",-3.55167f,40.1243410f);

        tweetDAO.insert(m);
        tweetDAO.insert(m1);
        tweetDAO.insert(m2);
        tweetDAO.insert(m3);
        tweetDAO.insert(m4);
        tweetDAO.insert(m5);

*/




    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
