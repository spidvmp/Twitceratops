package com.nicatec.twitceratops.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class TwitceratopsProvider extends ContentProvider {

    public static final String TWITCERATOPS_PROVIDER = "com.nicatec.twitceratops.provider";
    public static final Uri TWEETS_URI = Uri.parse("content://" + TWITCERATOPS_PROVIDER + "/tweets");

    private static final UriMatcher urimatcher;
    //constantes para diferenciar entre las distintas Uri Request
    private static final int ALL_TWEETS = 1;
    private static final int SINGLE_TWEET = 2;

    static {
        urimatcher = new UriMatcher(UriMatcher.NO_MATCH);
        urimatcher.addURI(TWITCERATOPS_PROVIDER, "tweets", ALL_TWEETS);
        urimatcher.addURI(TWITCERATOPS_PROVIDER, "tweets/#", SINGLE_TWEET);
    }

    private DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        return false;
    }

    private DBHelper getDbHelper() {
        if ( dbHelper == null ){
            dbHelper = DBHelper.getInstance();
        }
        return dbHelper;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (urimatcher.match(uri)){
            case ALL_TWEETS:
                return "vnd.android.cursor.dir/vnd.nicatec.tweets";
            case SINGLE_TWEET:
                return "vnd.android.cursor.item/vnd.nicatec.tweetMessage";
            default:
                throw new IllegalArgumentException("Unssuported URI: " + uri);
        }

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        TweetDAO tweetDAO = new TweetDAO(getContext());
        TweetMessage tweetMessage = new TweetMessage();
        long id = tweetDAO.insert(tweetMessage);
        if ( id > -1 ){
            Uri insertedUri = null;
            switch (urimatcher.match(uri)){
                case ALL_TWEETS:
                    insertedUri = ContentUris.withAppendedId(TWEETS_URI, id);
                    break;
                case SINGLE_TWEET:
                    insertedUri = ContentUris.withAppendedId(TWEETS_URI, id);
                    break;
                default:
                    break;
            }

            //notifico a los observadores que se ha insertado algo
            getContext().getContentResolver().notifyChange(uri, null);
            getContext().getContentResolver().notifyChange(insertedUri, null);

            return insertedUri;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
