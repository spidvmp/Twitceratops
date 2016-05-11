package com.nicatec.twitceratops.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by vtx on 11/5/16.
 */
public class CoordinatesHelper {

    public interface OnCoordinatesLocatedFinished {
        public void newCoordinatesLocated(LatLng coordinate);
    }

    public void getCoordinatesOfALocation(final String locationString, final Context context, final OnCoordinatesLocatedFinished block) {
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                Geocoder gc = new Geocoder(context);

                if ( gc.isPresent() ) {

                    List<Address> list = null;
                    try {
                        //obtener las coordenadas del sitio
                        list = gc.getFromLocationName(locationString, 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (list.size() == 1) {
                        if (list.get(0).hasLatitude() && list.get(0).hasLongitude()) {
                            LatLng position = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());

                            //guardo la ultima posicion puesta
                            UserDefaults def = new UserDefaults(context);
                            def.setCoordinates(position);

                            block.newCoordinatesLocated(position);
                        }

                    } else {
                        //por algun motivo no tengo coordenadas
                        block.newCoordinatesLocated(null);
                    }
                }

            }
        };

        Thread thread = new Thread(runable);
        thread.start();
    }
}
