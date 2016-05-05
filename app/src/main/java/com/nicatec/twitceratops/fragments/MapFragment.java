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
import com.nicatec.twitceratops.adapters.TweeterMessageAdapter;
import com.nicatec.twitceratops.model.TweetDAO;
import com.nicatec.twitceratops.model.Tweets;

import butterknife.ButterKnife;


public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {


    private static GoogleMap map;
    MapView mapView;

    //@Bind(R.id.fragmet_map_recycler_view)
    //RecyclerView mapRecyclerView;
    //@Bind(R.id.fragment_map)
    //Fragment fragmentMap;

    TweetDAO tweetDAO;
    Tweets tweets;
    TweeterMessageAdapter adapter;



    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);

        ButterKnife.bind(this, view);

        //mapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tweetDAO = new TweetDAO(getContext());

        //ponemos el mapa
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //mapView = (MapView) fragmentMap;

        refreshView();
        return view;
    }

    public void refreshView() {
        Log.v("","RefreshView");

        tweets = tweetDAO.query();
        adapter = new TweeterMessageAdapter(tweets, getActivity());
        //mapRecyclerView.setAdapter( adapter);

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng a = new LatLng(40.42234, -3.6976);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(a,15));

    }
}
