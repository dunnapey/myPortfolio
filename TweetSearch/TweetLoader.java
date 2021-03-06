package com.dunnaway;

import com.google.gson.Gson;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Peyton on 5/5/2017.
 */
public class TweetLoader {
    private Twitter twitter;
    private BYUITweet tweet;
    private Gson gson;

    private TreeMap<String, BYUITweet> tweetMap = new TreeMap<>();

    public TweetLoader() {
        configureKeys();
    }

    private void configureKeys() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("consumerKey")
                .setOAuthConsumerSecret("consumerSecret")
                .setOAuthAccessToken("accessToken")
                .setOAuthAccessTokenSecret("tokenSecret")
                .setJSONStoreEnabled(true);

        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }

    public TreeMap<String, BYUITweet> retrieveTweetsWithHashTag(String hashtag) {
        List<Status> results = new ArrayList<>();
        Query query = new Query();
        query.setQuery(hashtag);

        try {
            QueryResult result = twitter.search(query);
            results = result.getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        for (Status status : results) {
            String jsonString = TwitterObjectFactory.getRawJSON(status);
            gson = new Gson();
            tweet = gson.fromJson(jsonString, BYUITweet.class);
            tweetMap.put(tweet.getUser().getName(), tweet);
        }
        return tweetMap;
    }
}
