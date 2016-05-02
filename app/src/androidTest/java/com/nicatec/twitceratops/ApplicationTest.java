package com.nicatec.twitceratops;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.nicatec.twitceratops.model.TweetMessage;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test_Creo_Un_Mensaje() {
        final String mens = "Hola";
        final String photo = "url";
        final float lat = 5.9839453f;
        final float lon = -45.2346234f;
        final TweetMessage m = new TweetMessage(0,mens, photo, lat, lon);
        assertEquals(m.getMessage() , mens);
        assertEquals(m.getPhotoUrl(), photo);
        assertEquals(m.getLatitude(), lat);
        assertEquals(m.getLongitude(), lon);
    }

    public void test_Creo_Mensaje_Con_Valores_Nulos(){
        final String mens = "";
        final String photo = "";
        final float lat = 5.9839453f;
        final float lon = -45.2346234f;
        final TweetMessage m = new TweetMessage(0,null, null, lat, lon);
        assertEquals(m.getMessage() , mens);
        assertEquals(m.getPhotoUrl(), photo);
        assertEquals(m.getLatitude(), lat);
        assertEquals(m.getLongitude(), lon);

    }
}