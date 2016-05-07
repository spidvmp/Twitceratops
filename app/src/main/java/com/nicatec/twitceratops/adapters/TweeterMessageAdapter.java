package com.nicatec.twitceratops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.model.TweetMessage;
import com.nicatec.twitceratops.model.Tweets;
import com.nicatec.twitceratops.views.MapIconViewHolder;


/**
 * Created by vtx on 2/5/16.
 * Se exteiendo de RecyclerView.adapter que trabaja con MapIconViewholder y lo que viene a ser el el fetchresultscontroller
 * para que el recyclerview obtenga los datos que se porporciona el adapter y los muestre usando el viewholder
 */
public class TweeterMessageAdapter extends RecyclerView.Adapter<MapIconViewHolder> {

    private LayoutInflater layoutInflater;
    private Tweets tweets;

    public TweeterMessageAdapter(Tweets tweets, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.tweets = tweets;
    }


    @Override
    public MapIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //asocio la vista para que utilice el layout definido en icon_tweet_message_map
        //esto crea las vistas que necesite, 8, 10, las que sean
        View view = layoutInflater.inflate(R.layout.icon_tweet_message_map, parent, false);
        return new MapIconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MapIconViewHolder holder, int position) {
        //esto se llama cada vez que tiene que repintar las celdas, tantas veces como celdas hayan
        //esto habria que rehacer el que informacion le paso
        Log.v("ADAPTER", "Envio " + tweets.get(position).getMessage());

        TweetMessage tm = tweets.get(position);
        holder.setTweetMessage(tm);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
