package com.nicatec.twitceratops.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

public class TweetDAO {
	/*
	Solo me intersa el metodo de insertar, el de borrar, obtener un registro en concreto y el de buscar todos los registros.
	Se supone que hay que mostrar todo lo que haya en la BD, ya que lo genero la librearia de tweeter
	 */

	/*
	public static final String TABLE_RADAR = "RADAR";

	// Table field constants 
	public static final String KEY_RADAR_ID = "_id";
	public static final String KEY_RADAR_TITLE = "name";
	public static final String KEY_RADAR_DESCRIPTION = "description";

	public static final String SQL_CREATE_RADAR_TABLE =
			"create table " + TABLE_RADAR
					+ "( " + KEY_RADAR_ID
					+ " integer primary key autoincrement, "
					+ KEY_RADAR_TITLE + " text not null,"
					+ KEY_RADAR_DESCRIPTION + " text not null"
					+ ");";


	public static final String[] allColumns = {
			KEY_RADAR_ID,
			KEY_RADAR_TITLE,
			KEY_RADAR_DESCRIPTION
	};
	*/

	protected DBHelper db;

	private static final long INVALID_ID_DELETE_ALL_RECORDS = 0;
	//private String databaseName;

	private WeakReference<Context> context;
	// private Context context;



	public TweetDAO( Context context) {
		this.context = new WeakReference<Context>(context);
		db = DBHelper.getInstance();
		//this.databaseName = databaseName;
	}

	public String getTableName() { return DBConstants.TABLE_TWEETS; }
	public long insert(TweetMessage message) {
		//inserto un tweet en la BD, con todos los datos que disponga
		if (message == null) {
			throw new IllegalArgumentException("Passing NULL message, imbecile");
		}

		if (context.get() == null) {
			throw new IllegalStateException("Context NULL");
		}

		// insert
		//DBHelper db = DBHelper.getInstance(this.databaseName, context.get());

		long id = db.getWritableDatabase().insert(getTableName(), null, this.getContentValues(message));
		message.setId(id);
		db.close();

		Log.v("TweetDAO", "Insertado " + message.getMessage());
		return id;
	}

	public void deleteAll(){
		Log.v("TweetDAO","Borro todos los registros");
		db.getWritableDatabase().delete(getTableName(),null,null);

	}


	public @Nullable
	TweetMessage getTweetQuery(long id) {
		//devuelve un tweet con todos los datos que corresponda con el id de la BD
		TweetMessage tweetMessage = null;

		String where = DBConstants.KEY_TWEET_ID + "=" + id;
		Cursor c = db.getReadableDatabase().query(DBConstants.TABLE_TWEETS, DBConstants.allColumns, where, null, null, null, null);
		if ( c != null ) {
			if ( c.getCount() > 0 ){
				//se supone que solo puede haber uno
				c.moveToFirst();
				tweetMessage = elementFromCursor(c);
			}
		}

		db.close();
		return tweetMessage;
	}


	/*

	public int update(long id, Radar message) {
		if (message == null) {
			throw new IllegalArgumentException("Passing NULL message, imbecile");
		}
		if (context.get() == null) {
			throw new IllegalStateException("Context NULL");
		}

		DBHelper db = DBHelper.getInstance(this.databaseName, context.get());

		//db.getWritableDatabase().update(TABLE_RADAR, this.getContentValues(message), KEY_RADAR_ID + "=" + id, null);
		int numberOfRowsUpdated = db.getWritableDatabase().update(TABLE_RADAR, this.getContentValues(message), KEY_RADAR_ID + "=?", new String[]{"" + id});

		db.close();
		db=null;
		return numberOfRowsUpdated;
	}

	public void delete(long id) {
		DBHelper db = DBHelper.getInstance(this.databaseName, context.get());

		if (id == INVALID_ID_DELETE_ALL_RECORDS) {
			db.getWritableDatabase().delete(TABLE_RADAR,  null, null);
		} else {
			db.getWritableDatabase().delete(TABLE_RADAR, KEY_RADAR_ID + " = " + id, null);
		}
		db.close();
		db=null;
	}

	public void deleteAll() {
		delete(INVALID_ID_DELETE_ALL_RECORDS);
	}
*/
	public static ContentValues getContentValues(TweetMessage message) {
		ContentValues content = new ContentValues();
		content.put(DBConstants.KEY_TWEET_NAME, message.getMessage());
		content.put(DBConstants.KEY_PHOTO_URL, message.getPhotoUrl());
		content.put(DBConstants.KEY_LATITUDE, message.getLatitude());
		content.put(DBConstants.KEY_LONGITUDE, message.getLongitude());
		if ( message.getId() > 0) {
			content.put(DBConstants.KEY_TWEET_ID, message.getId());
		}

		return content;
	}

	// convenience method

	public static TweetMessage elementFromCursor(Cursor c) {
		//me poasan un cursor donde hay un Tweetmessage, creo un objeto Tweet y le coloco los valores y lo devuelvo
		assert c != null;

		long id = c.getLong(c.getColumnIndex(DBConstants.KEY_TWEET_ID));
		String message = c.getString(c.getColumnIndex(DBConstants.KEY_TWEET_NAME));
		String photo = c.getString(c.getColumnIndex(DBConstants.KEY_PHOTO_URL));
		float lat = c.getFloat(c.getColumnIndex(DBConstants.KEY_LATITUDE));
		float lon = c.getFloat(c.getColumnIndex(DBConstants.KEY_LONGITUDE));

		TweetMessage tweetMessage = new TweetMessage(id, message, photo, lat, lon);

		return tweetMessage;
	}


	/**
	 * Returns all radars in DB inside a Cursor
	 * @return cursor with all records
	 */

	/*
	public Cursor queryCursor() {
		// select
		DBHelper db = DBHelper.getInstance(this.databaseName, context.get());

		Cursor c = db.getReadableDatabase().query(TABLE_RADAR, allColumns, null, null, null, null, null);

		return c;
	}

	public Radars query() {
		Radars radars = new Radars();

		Cursor cursor = queryCursor();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				Radar radar = radarFromCursor(cursor);
				radars.add(radar);
			} while (cursor.moveToNext());
		}
		return radars;
	}

*/
	/**
	 * Returns a Radar object from its id
	 * @param id - the radar id in db
	 * @return Radar object if found, null otherwise
	 */

	/*
	public Radar query(long id) {
		Radar radar = null;

		DBHelper db = DBHelper.getInstance(this.databaseName, context.get());

		String where = KEY_RADAR_ID + "=" + id;
		Cursor c = db.getReadableDatabase().query(TABLE_RADAR, allColumns, where, null, null, null, null);
		if (c != null) {
			if (c.getCount() > 0) {
				c.moveToFirst();
				radar = radarFromCursor(c);
			}
		}
		c.close();
		db.close();
		return radar;
	}

*/
}
