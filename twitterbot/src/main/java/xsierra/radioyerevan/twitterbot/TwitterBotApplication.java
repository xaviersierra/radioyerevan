package xsierra.radioyerevan.twitterbot;

import xsierra.radioyerevan.cli.JokeFinder;
import xsierra.radioyerevan.cli.RandomJokeFinder;

public class TwitterBotApplication {

    private static final String CONSUMER_KEY = "CONSUMER_KEY";
    private static final String CONSUMER_SECRET = "CONSUMER_SECRET";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET";

    private static final JokeFinder finder = new RandomJokeFinder();

    public static void main(String[] args){
        String consumerKey = System.getenv(CONSUMER_KEY);
        String consumerSecret = System.getenv(CONSUMER_SECRET);
        String accessToken = System.getenv(ACCESS_TOKEN);
        String accessTokenSecret = System.getenv(ACCESS_TOKEN_SECRET);

        JokeSender sender = new TweetSender(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        sender.sendJoke(finder.findJoke());
    }

}
