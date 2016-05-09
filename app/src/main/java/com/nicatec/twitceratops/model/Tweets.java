package com.nicatec.twitceratops.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vtx on 2/5/16.
 */
public class Tweets {
    List<TweetMessage> tweets;

    public static Tweets createTweets(List<TweetMessage> tweets) {
        Tweets myTweets = createTweets();

        for (TweetMessage t: tweets) {
            myTweets.add(t);
        }

        return myTweets;
    }

    public static Tweets createTweets() {
        Tweets myTweets = new Tweets();
        return myTweets;
    }

    private Tweets() { }

    public int size() { return getTweets().size(); }

    public TweetMessage get(int index) { return getTweets().get(index);}

    public void remove(TweetMessage t) { getTweets().remove(t); }

    public void clear() { getTweets().clear(); }

    public void add(TweetMessage t) {getTweets().add(t); }

    public List<TweetMessage> getTweets() {
        if (this.tweets == null) {
            this.tweets = new LinkedList<>();
        }
        return this.tweets;
    }
}
