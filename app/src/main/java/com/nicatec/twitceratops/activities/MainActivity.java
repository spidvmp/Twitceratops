package com.nicatec.twitceratops.activities;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.fragments.MapFragment;
import com.nicatec.twitceratops.fragments.SearchTextViewFragment;
import com.nicatec.twitceratops.model.TweetDAO;
import com.nicatec.twitceratops.model.Tweets;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchTextViewFragment.SearchedTextListened, OnMapReadyCallback, LocationListener {

    @Bind(R.id.button)
    Button button;

    @Bind(R.id.fragment_search_text_view)
    FrameLayout fragmentSearchView;

    //@Bind(R.id.activity_main_fragment_map)
    //FrameLayout fragmentMap;

    private SearchTextViewFragment searchTextViewFragment;
    private MapFragment mapFragment;


    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //al arrancar comienzo con lo qu haya en la BD
        TweetDAO tweetDAO = new TweetDAO(getApplicationContext());
        Tweets tweets = tweetDAO.query();
/*
        FragmentManager fm = getSupportFragmentManager();
        if ( fm != null ){

            mapFragment = new MapFragment();
            fm.beginTransaction()
                    .add(R.id.activity_main_fragment_map , mapFragment)
                    .commit();
        }

*/

        //pongo mi posicion

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map1);
        mapFragment.getMapAsync(this);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launchTwitter();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if ( item.getItemId() == R.id.action_search) {
        //if ( item == actionSearch) {

            FragmentManager fm = getSupportFragmentManager();

            if ( fm != null ){

                searchTextViewFragment  = new SearchTextViewFragment();
                fm.beginTransaction()
                        .add(fragmentSearchView.getId(),  searchTextViewFragment)
                        .commit();

            }

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void OnNewLocationToSearch(String locationString) {
        Log.v("MainActivity","OnNewLocationToSearch recibe para buscar:" + locationString);
        //quito el fragment
        FragmentManager fm = getSupportFragmentManager();
        if ( fm != null ){
            MapFragment mf = new MapFragment();
            fm.beginTransaction()
                    .remove(searchTextViewFragment)
                    .commit();
        }

        Geocoder gc = new Geocoder(this, Locale.getDefault());

        if ( gc.isPresent() ) {


            try {
                //obtener las coordenadas del sition
                List<Address> list = gc.getFromLocationName("castillo de simancas 2, Las Rozas Madrid", 1);
                Log.v("", "List=" + list.toString());

                //pasar las coordenadas a twitter y que se encargue de guardar en la BD

                //Address address = list.get(0);
                //Log.v("",address.toString());
                //double lat = address.getLatitude();
                //double lng = address.getLongitude();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //actualizo la vista
            //mapFragment.refreshView();
            LatLng sydney = new LatLng(40.42234, -3.6976);
            //map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        } else {
            Log.v("Geocoder","No hay servicio ecxterno");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setMyLocationEnabled(true);


        //LatLng sydney = new LatLng(40.42234, -3.6976);
        //map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        //map.animateCamera(CameraUpdateFactory.zoomTo(15));
        /*
        Marker aqui = map.addMarker(new MarkerOptions()

                .position(sydney)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.app))
                .title("Aqui"));
        aqui.showInfoWindow();
        */
        Log.v("MAPREADY","MAPA CARGADO----------------------------");

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.v("MainActivity","Nueva localizacion del mapa " + location);

    }
}
