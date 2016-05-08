package com.nicatec.twitceratops.model;

/**
 * Created by vtx on 21/4/16.
 */
public class DBConstants {
	//nombre de la BD
	public static final String DBNAME = "Twitter.sqlite";
	//nombre de la tabla
	public static final String TABLE_TWEETS = "TWEETS";
	// Table field constants
	public static final String KEY_TWEET_ID = "_id";
	public static final String KEY_TWEET_NAME = "name";
	public static final String KEY_PHOTO_URL = "photourl";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";

	public static final String[] allColumns = {
			KEY_TWEET_ID,
			KEY_TWEET_NAME,
			KEY_PHOTO_URL,
			KEY_LATITUDE,
			KEY_LONGITUDE
	};
	public static final String[] centerCoordinate = {
			KEY_LATITUDE,
			KEY_LONGITUDE
	};
	public static final String SQL_CREATE_TWEETS_TABLE =
			"create table " + TABLE_TWEETS
					+ "( " + KEY_TWEET_ID
					+ " integer primary key autoincrement, "
					+ KEY_TWEET_NAME + " text not null,"
					+ KEY_PHOTO_URL + " text not null,"
					+ KEY_LATITUDE + " float,"
					+ KEY_LONGITUDE + " float"
					+ ");";
}
