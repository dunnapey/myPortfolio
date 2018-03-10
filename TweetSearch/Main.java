package com.dunnaway;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String hashtag;
        System.out.println("Enter a hashtag to search: ");
        hashtag = scanner.nextLine();
        TweetLoader tweetLoader = new TweetLoader();
        Map<String, BYUITweet> tweets = (tweetLoader.retrieveTweetsWithHashTag(hashtag));

        for (String name : tweets.keySet()) {
            BYUITweet tweet = tweets.get(name);
            System.out.println(tweet.getUser().getName() + " (" + tweet.getUser().getFollowers() + " Followers) - " + tweet.getText());
        }
    }
}
