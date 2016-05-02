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
        TweetMessage m = new TweetMessage(0,"hola","kkkkk",-3.55244f,40.1243512f);

        tweetDAO.insert(m);
        */

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
