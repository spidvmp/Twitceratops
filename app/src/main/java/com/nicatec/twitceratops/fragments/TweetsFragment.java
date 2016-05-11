package com.nicatec.twitceratops.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.adapters.TweeterMessageAdapter;
import com.nicatec.twitceratops.model.DBConstants;
import com.nicatec.twitceratops.model.TweetDAO;
import com.nicatec.twitceratops.model.TweetMessage;
import com.nicatec.twitceratops.model.Tweets;
import com.nicatec.twitceratops.model.TwitceratopsProvider;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TweetsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


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

        //defino el loader para que se ejecute
        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    public void hola() {

    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        switch (id){
            case 0:
                loader = new CursorLoader(getActivity(), TwitceratopsProvider.TWEETS_URI, DBConstants.allColumns, null, null, null);
                break;

        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        Tweets tweets = Tweets.createTweets();

        while (cursor.moveToNext()) {
            TweetMessage tweetMessage = new TweetDAO(getActivity()).elementFromCursor(cursor);
            tweets.add(tweetMessage);
        }

        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TweeterMessageAdapter adapter = new TweeterMessageAdapter(tweets, getContext());
        tweetsRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
