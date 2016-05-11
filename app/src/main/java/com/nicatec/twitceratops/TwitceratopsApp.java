package com.nicatec.twitceratops;

import android.app.Application;
import android.util.Log;

import com.nicatec.twitceratops.model.DBConstants;
import com.nicatec.twitceratops.model.DBHelper;

public class TwitceratopsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //configutro la BD
        DBHelper.configure(DBConstants.DBNAME, getApplicationContext());

        Log.v("TWITCERATOPSAPP","Descomentar para insertar registros");
        /*
        TweetDAO tweetDAO = new TweetDAO(getApplicationContext());
        TweetMessage m = new TweetMessage(0,"Hoy comemos macarrones","http://www.nicatec.com/android/macarrones.jpg",40.4043592f, -3.70274f);
        TweetMessage m1 = new TweetMessage(0,"Los albondigas en remojo","http://www.nicatec.com/android/albondigas.jpg",40.4243882f, -3.68200f);
        TweetMessage m2 = new TweetMessage(0,"Una flor en el culo","http://www.nicatec.com/android/coliflor.jpg",40.439108f, -3.71302f);
        //estos comentados dn error. lo mismo que el formato de la imagen
        //TweetMessage m3 = new TweetMessage(0,"Como diria Julio, y lo sabes","http://www.nicatec.com/android/papaya.jpg",40.393490f, -3.70780f);
        //TweetMessage m4 = new TweetMessage(0,"De la abuela","http://www.nicatec.com/android/croquetas.jpg",40.42933780f, -3.700067f);
        TweetMessage m5 = new TweetMessage(0,"Y con este postre se me acabo la imaginacion","http://www.nicatec.com/android/tiramisu.jpg",40.43410f, -3.720078f);

        tweetDAO.insert(m);
        tweetDAO.insert(m1);
        tweetDAO.insert(m2);
        //tweetDAO.insert(m3);
        //tweetDAO.insert(m4);
        tweetDAO.insert(m5);
        */

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
