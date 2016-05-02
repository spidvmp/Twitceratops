package com.nicatec.twitceratops;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.nicatec.twitceratops.model.TweetMessage;
import com.nicatec.twitceratops.model.Tweets;

import java.util.LinkedList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    final String mens = "Hola";
    final String photo = "url";
    final float lat = 5.9839453f;
    final float lon = -45.2346234f;

    public void test_Creo_Un_Mensaje() {

        final TweetMessage m = new TweetMessage(0,mens, photo, lat, lon);
        assertEquals(m.getMessage() , mens);
        assertEquals(m.getPhotoUrl(), photo);
        assertEquals(m.getLatitude(), lat);
        assertEquals(m.getLongitude(), lon);
    }

    public void test_Creo_Mensaje_Con_Valores_Nulos(){
        final TweetMessage m = new TweetMessage(0,null, null, lat, lon);
        assertEquals(m.getMessage() , "");
        assertEquals(m.getPhotoUrl(), "");
        assertEquals(m.getLatitude(), lat);
        assertEquals(m.getLongitude(), lon);

    }

    public void test_Creo_Mensajes_En_La_Lista_De_Tweets_Y_Los_Borro() {
        final TweetMessage m1 = new TweetMessage(0,mens,photo,lat,lon);
        final TweetMessage m2 = new TweetMessage(0,"sss","photo",lat,lon);
        List<TweetMessage> tm = new LinkedList<>();

        Tweets tw = Tweets.createTweets(tm);
        tw.add(m1);
        tw.add(m2);
        assertEquals(tw.size(),2);

        tw.remove(m1);
        assertEquals(tw.size(),1);

        tw.clear();
        assertEquals(tw.size(),0);

    }
}