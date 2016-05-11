package com.nicatec.twitceratops.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nicatec.twitceratops.activities.MainActivity;
import com.nicatec.twitceratops.model.TweetMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MapIconViewHolder extends RecyclerView.ViewHolder {

    //me pasan el mensaje, donde va el texto a imprimer , la imagen y las coordenadas de donde pintarlo

    public MapIconViewHolder(View itemView) {
        super(itemView);

        //ButterKnife.bind(this, itemView);


    }

    public void setTweetMessage(TweetMessage m, final Context context){
        //me pasan los datos completos y deberia bajarse la foto y configurar el mensaje cuando se pulse sobre el icono
        final TweetMessage message = m;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //final GoogleMap mapilla = MainActivity.map;
                Bitmap originPhoto = download_Image(message.getPhotoUrl());
                //le cambio el tamaño para que sea de 50x50
                //try {
                    final Bitmap ph = Bitmap.createScaledBitmap(originPhoto, 50, 50, true);
                //}catch () {
                //    final Bitmap ph = null;
                //}

                Handler mainHandler = new Handler(context.getMainLooper());

                Runnable r = new Runnable() {
                    @Override
                    public void run() {

                        MarkerOptions m = new MarkerOptions()
                                .position(new LatLng(message.getLatitude(), message.getLongitude()))
                                .title(message.getMessage());
                        //si tengo imagen la pongo
                        if ( ph != null ){
                                m.icon(BitmapDescriptorFactory.fromBitmap(ph));
                        }
                        MainActivity.addMark(m);
                    }
                };

                mainHandler.post(r);

                }
        });

        thread.start();



    }

    private Bitmap download_Image(String url) {

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

    }

}

