package com.nicatec.twitceratops.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.activities.MainActivity;
import com.nicatec.twitceratops.model.TweetMessage;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by vtx on 2/5/16.
 */
public class MapIconViewHolder extends RecyclerView.ViewHolder {

    //me pasan el mensaje, donde va el texto a imprimer , la imagen y las coordenadas de donde pintarlo
    private TweetMessage tweetMessage;

    //necesitamos una imagen, que sera el icono que aparece en el mapa
    @Bind(R.id.icon_image_view_map_icon_view_holder)
    ImageView imageIconImageView;
    @Bind(R.id.nombre)
    TextView nombre;

    public MapIconViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);


    }

    public void setTweetMessage( TweetMessage m){
        //me pasan los datos completos y deberia bajarse la foto y configurar el mensaje cuando se pulse sobre el icono
        Log.v("ViewHolder","Mostrando " + m.getMessage());
        GoogleMap mapilla = MainActivity.map;
        if ( mapilla != null ) {
            Marker marker = mapilla.addMarker( new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                    .title(m.getMessage())
            );

            nombre.setText(m.getMessage());
        }
    }
}
