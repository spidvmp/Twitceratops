package com.nicatec.twitceratops.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class TweetDAO {
	/*
	Solo me intersa el metodo de insertar,todos los elementos, el de borrar, obtener un registro en concreto y el de buscar todos los registros.
	Se supone que hay que mostrar todo lo que haya en la BD, ya que lo genero la librearia de tweeter
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

    public Tweets query() {
        //genera un listado de todos los elementos de la tabla
        List<TweetMessage> tweetMessagesList = new LinkedList<>();

        Cursor cursor = queryCursor();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                TweetMessage m = elementFromCursor(cursor);
                tweetMessagesList.add(m);
            } while (cursor.moveToNext());
        }
        Tweets tweets = Tweets.createTweets(tweetMessagesList);
        return tweets;
    }

	public LatLng centerQuery() {
		//HAce la media de las coordenadas y saca el punto central
		LatLng center = null;
		Cursor cursor = centerCursor();
		if ( cursor != null && cursor.getCount() >0) {
			cursor.moveToFirst();
			//se supone 	ue solo hay un registro, ya que es la media de las coordenadas
			do {
				center = coordinatesFromCursor(cursor);
			} while ( cursor.moveToNext());
		}
		return center;
	}

	public Cursor centerCursor(){
		//hace la media de la latitud y la media de la longitud de todos los twits, de forma que tengo el punto medio
		//para el mapa

		Cursor c = db.getReadableDatabase().query(DBConstants.TABLE_TWEETS,
				DBConstants.centerCoordinate,
				null,
				null,
				null,
				null,
				null
		);

		return c;
	}
    public Cursor queryCursor() {
        // select

        Cursor c = db.getReadableDatabase().query(DBConstants.TABLE_TWEETS,
                DBConstants.allColumns,
                null, // selection
                null, //  selectionArgs
                null, // groupBy
                null, // having
                DBConstants.KEY_TWEET_ID  // orderBy
        );

        return c;
    }

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

	public static LatLng coordinatesFromCursor(Cursor c){
		assert  c != null;
		float lat = c.getFloat(c.getColumnIndex(DBConstants.KEY_LATITUDE));
		float lon = c.getFloat(c.getColumnIndex(DBConstants.KEY_LONGITUDE));

		return new LatLng(lat, lon);
	}

}
