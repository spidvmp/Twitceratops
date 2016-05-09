package com.nicatec.twitceratops.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class TwitceratopsProvider extends ContentProvider {

    public static final String TWITCERATOPS_PROVIDER = "com.nicatec.twitceratops.provider";
    public static final Uri TWEETS_URI = Uri.parse("content://" + TWITCERATOPS_PROVIDER + "/tweets");

    private static final UriMatcher uriMatcher;
    //constantes para diferenciar entre las distintas Uri Request
    private static final int ALL_TWEETS = 1;
    private static final int SINGLE_TWEET = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TWITCERATOPS_PROVIDER, "tweets", ALL_TWEETS);
        uriMatcher.addURI(TWITCERATOPS_PROVIDER, "tweets/#", SINGLE_TWEET);
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = getDbHelper().getWritableDatabase();

        // Replace these with valid SQL statements if necessary.
        String groupBy = null;
        String having = null;
        // Use an SQLite Query Builder to simplify constructing the database query.

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DBConstants.TABLE_TWEETS);

        // If this is a row query, limit the result set to the passed in row.
        String rowID = null;
        switch (uriMatcher.match(uri)) {
            case SINGLE_TWEET:
                rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DBConstants.KEY_TWEET_ID + "=" + rowID);
                break;
            default: break;
        }

        // Specify the table on which to perform the query. This can // be a specific table or a join as required. queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);
        // Execute the query.
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
        // Return the result Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
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
    public Uri insert(Uri uri, ContentValues values) {
        TweetDAO tweetDAO = new TweetDAO(getContext());
Log.v("","name=" + values.get("name"));
        TweetMessage tweetMessage = new TweetMessage(0,"dd","foto",0.0f,0.0f);
        long id = tweetDAO.insert(tweetMessage);
        if ( id > -1 ){
            Uri insertedUri = null;
            switch (uriMatcher.match(uri)){
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
