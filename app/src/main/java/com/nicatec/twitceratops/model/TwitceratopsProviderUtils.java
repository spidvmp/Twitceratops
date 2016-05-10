package com.nicatec.twitceratops.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by vtx on 9/5/16.
 */
public class TwitceratopsProviderUtils {

    // convenience methods: easy access to this content provider from within this project

    public static String getIdFromUri(Uri uri) {
        String rowID = uri.getPathSegments().get(1);
        return rowID;
    }


    public static Tweets getAllTweets(Context context) {
        ContentResolver cr = context.getContentResolver();

        Cursor cursor = cr.query(TwitceratopsProvider.TWEETS_URI, DBConstants.allColumns, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        Tweets tweets = Tweets.createTweets();

        while (cursor.moveToNext()) {
            TweetMessage tweetMessage = new TweetDAO(context).elementFromCursor(cursor);
            tweets.add(tweetMessage);
        }

        return tweets;
    }

    public static Uri insertTweetMessage(Context context, TweetMessage tweetMessage) {
        ContentResolver cr = context.getContentResolver();
        if (tweetMessage == null) {
            return null;
        }

        //Uri uri = cr.insert(TwitceratopsProvider.TWEETS_URI, (new TweetDAO(context)).getContentValues(tweetMessage));
        Uri uri = cr.insert(TwitceratopsProvider.TWEETS_URI,  TweetDAO.getContentValues(tweetMessage));
        tweetMessage.setId(Long.parseLong(getIdFromUri(uri)));
        return uri;
    }

    /*
    public static void deleteNotebook(Context context, Notebook notebook) {
        ContentResolver cr = context.getContentResolver();
        String sUri = EverpobreProvider.NOTEBOOKS_URI.toString() + "/" + notebook.getId();
        Uri uri = Uri.parse(sUri);
        cr.delete(uri, null, null);
    }
    */
}
