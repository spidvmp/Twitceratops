package com.nicatec.twitceratops.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by vtx on 10/5/16.
 */
public class UserDefaults {
    //vamos a rememorar un poco a IOS

    private Context context;
    SharedPreferences preferences;

    public UserDefaults(Context c) {
        this.context = c;
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    public void setCoordinates(LatLng coordinates) {
        SharedPreferences.Editor  editor = preferences.edit();

        editor.putFloat("lat", (float) coordinates.latitude);
        editor.putFloat("lon", (float) coordinates.longitude);
        editor.commit();
    }

    public LatLng getCoordinates(){

        final float defaultValue = -1000.0f;
        if ( preferences.getFloat("lat", defaultValue) == defaultValue) {
            //No tengo valores, ha salido por el valor por defecto
            return null;
        }

        LatLng coordinates = new LatLng(preferences.getFloat("lat",0.0f), preferences.getFloat("lon",0.0f));

        return coordinates;
    }
}
