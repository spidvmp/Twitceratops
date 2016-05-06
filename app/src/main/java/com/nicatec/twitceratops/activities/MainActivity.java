package com.nicatec.twitceratops.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.fragments.MapFragment;
import com.nicatec.twitceratops.fragments.SearchTextViewFragment;
import com.nicatec.twitceratops.model.TweetDAO;
import com.nicatec.twitceratops.model.Tweets;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchTextViewFragment.SearchedTextListened {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.fragment_search_text_view)
    FrameLayout fragmentSearchView;
    @Bind(R.id.activity_main_fragment_map)
    FrameLayout fragmentMap;

    private SearchTextViewFragment searchTextViewFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //al arrancar comienzo con lo qu haya en la BD
        TweetDAO tweetDAO = new TweetDAO(getApplicationContext());
        Tweets tweets = tweetDAO.query();

        FragmentManager fm = getSupportFragmentManager();
        if ( fm != null ){

            mapFragment = new MapFragment();
            fm.beginTransaction()
                    .add(R.id.activity_main_fragment_map , mapFragment)
                    .commit();
        }


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
            try {
                List<Address> list = gc.getFromLocationName("Madrid", 1);
                Log.v("","List=" + list.toString());
                //Address address = list.get(0);
                //Log.v("",address.toString());
                //double lat = address.getLatitude();
                //double lng = address.getLongitude();
            } catch (Exception e) {
                e.printStackTrace();
            }

        //actualizo la vista
        mapFragment.refreshView();
    }
}
