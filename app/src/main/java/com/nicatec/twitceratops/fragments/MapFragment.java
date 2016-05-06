package com.nicatec.twitceratops.fragments;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.nicatec.twitceratops.R;

import butterknife.ButterKnife;


//public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener
   public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener{


    private static GoogleMap map;
    MapView mapView;

    //@Bind(R.id.fragmet_map_recycler_view)
    //RecyclerView mapRecyclerView;
    //@Bind(R.id.fragment_map)
    //View fragmentMap;
/*
    TweetDAO tweetDAO;
    Tweets tweets;
    TweeterMessageAdapter adapter;
    //RecyclerView recyclerView;
*/

    public MapFragment() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);

        ButterKnife.bind(this, view);

        //recyclerView = new RecyclerView(getContext());
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //mapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //tweetDAO = new TweetDAO(getContext());

        //ponemos el mapa
        mapView = (MapView) view.findViewById(R.id.fragment_map);
        mapView.onCreate(savedInstanceState);
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        //SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        //mapView = (MapView) fragmentMap;
        //mapFragment.getMapAsync(this);

        refreshView();
        return view;
    }

    public void refreshView() {
        Log.v("","RefreshView");

        LatLng a = new LatLng(40.42234, -3.6976);
        //MapFragment.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(a,15));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(a,15));

        //tweets = tweetDAO.query();
        //adapter = new TweeterMessageAdapter(tweets, getActivity());
        //mapRecyclerView.setAdapter( adapter);
        //recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.v("","Onlocationchanged");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //MapFragment.googleMap = googleMap;
        LatLng a = new LatLng(40.42234, -3.6976);
        //MapFragment.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(a,15));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(a,15));

    }




    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
