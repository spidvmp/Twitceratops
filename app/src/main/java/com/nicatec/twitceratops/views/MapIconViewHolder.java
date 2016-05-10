package com.nicatec.twitceratops.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nicatec.twitceratops.activities.MainActivity;
import com.nicatec.twitceratops.model.TweetMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.ButterKnife;


/**
 * Created by vtx on 2/5/16.
 */
public class MapIconViewHolder extends RecyclerView.ViewHolder {

    //me pasan el mensaje, donde va el texto a imprimer , la imagen y las coordenadas de donde pintarlo
    //private TweetMessage tweetMessage;

    //necesitamos una imagen, que sera el icono que aparece en el mapa
    //@Bind(R.id.icon_image_view_map_icon_view_holder)
    //ImageView imageIconImageView;
    //@Bind(R.id.nombre)
    //TextView nombre;

    public MapIconViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);


    }

    public void setTweetMessage( TweetMessage m){
        //me pasan los datos completos y deberia bajarse la foto y configurar el mensaje cuando se pulse sobre el icono
        //Log.v("ViewHolder","Mostrando " + m.getMessage());

        final TweetMessage message = m;
        new Thread(new Runnable() {
            @Override
            public void run() {
                GoogleMap mapilla = MainActivity.map;
                Bitmap ph = download_Image(message.getPhotoUrl());
                
                if ( mapilla != null ) {
                    Marker marker = mapilla.addMarker(new MarkerOptions()
                            .position(new LatLng(message.getLatitude(), message.getLongitude()))
                            .icon(BitmapDescriptorFactory.fromBitmap(ph))
                            .title(message.getMessage())

                    );
                }
            }
        }).start();


            //nombre.setText(m.getMessage());

    }

    private Bitmap download_Image(String url) {
        //---------------------------------------------------
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
        }
        return bm;
        //---------------------------------------------------
    }

}

