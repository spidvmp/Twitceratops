package com.nicatec.twitceratops.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.adapters.TweeterMessageAdapter;
import com.nicatec.twitceratops.model.TweetDAO;
import com.nicatec.twitceratops.model.Tweets;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MapFragment extends Fragment {


    @Bind(R.id.fragmet_map_recycler_view)
    RecyclerView mapRecyclerView;

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

        mapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //le pasamos el adaptador, se crea y le pasamos el contexto, tendremos que pasarle tb el listado de tweets
        //genero el listado de tweets
        /*
        tweetDAO = new TweetDAO(getContext());
        tweets = tweetDAO.query();
        adapter = new TweeterMessageAdapter(tweets, getActivity());
        mapRecyclerView.setAdapter( adapter);
        */
        refreshView();
        return view;
    }

    public void refreshView() {
        Log.v("","RefreshView");
        tweetDAO = new TweetDAO(getContext());
        tweets = tweetDAO.query();
        adapter = new TweeterMessageAdapter(tweets, getActivity());
        mapRecyclerView.setAdapter( adapter);

    }

}
