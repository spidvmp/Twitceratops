package com.nicatec.twitceratops.model;

import java.io.Serializable;

/**
 * Created by vtx on 30/4/16.
 */
public class TweetMessage  implements Serializable {
    private long id;
    private String message;
    private String photoUrl;
    private float latitude;
    private float longitude;



    public TweetMessage(long id, String message, String photoUrl, float latitude, float longitude) {
        if (message == null || message.isEmpty()) {
            this.message = "";
        } else {
            this.message = message;
        }

        //si id = 0 significa que no tiene relacion en la BD,
        if (id <= 0) {
            this.id = 0;
        } else {
            this.id = id;
        }

        //si viene la url de la foto vacia, pues que no de error, no guardo nada
        if ( photoUrl == null || photoUrl.isEmpty()){
            this.photoUrl = "";
        } else {
            this.photoUrl = photoUrl;
        }

        //en las coordenadas, vendra algo, si no dara un pete por otro motivo, pero un float viene
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
