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
        TweetMessage m = new TweetMessage(0,"hola","kkkkk",40.4043592f, -3.70274f);
        TweetMessage m1 = new TweetMessage(0,"adios","kkkkk",40.4243882f, -3.68200f);
        TweetMessage m2 = new TweetMessage(0,"cucu","kkkkk",40.439108f, -3.71302f);
        TweetMessage m3 = new TweetMessage(0,"trastras","kkkkk",40.393490f, -3.70780f);
        TweetMessage m4 = new TweetMessage(0,"saltaba","kkkkk",40.42933780f, -3.700067f);
        TweetMessage m5 = new TweetMessage(0,"la rana","kkkkk",40.43410f, -3.720078f);

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
