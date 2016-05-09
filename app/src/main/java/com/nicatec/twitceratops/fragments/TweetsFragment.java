package com.nicatec.twitceratops.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.adapters.TweeterMessageAdapter;
import com.nicatec.twitceratops.model.Tweets;
import com.nicatec.twitceratops.model.TwitceratopsProviderUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TweetsFragment extends Fragment {


    @Bind(R.id.fragment_tweets_recycler_view)
    RecyclerView tweetsRecyclerView;


    public TweetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tweets, container, false);

        ButterKnife.bind(this,view);

        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*
        TweetDAO tweetDAO = new TweetDAO(getContext());
        Tweets tweets = tweetDAO.query();
        */
        Tweets tweets = TwitceratopsProviderUtils.getAllTweets(getActivity());

        TweeterMessageAdapter adapter = new TweeterMessageAdapter(tweets, getContext());
        tweetsRecyclerView.setAdapter(adapter);



        return view;
    }

}
