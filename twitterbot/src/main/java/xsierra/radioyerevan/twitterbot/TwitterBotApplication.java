package xsierra.radioyerevan.twitterbot;

import xsierra.radioyerevan.cli.Joke;
import xsierra.radioyerevan.cli.JokeFinder;
import xsierra.radioyerevan.cli.RandomJokeFinder;

import java.util.Optional;

public class TwitterBotApplication {

    private static final String CONSUMER_KEY = "CONSUMER_KEY";
    private static final String CONSUMER_SECRET = "CONSUMER_SECRET";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET";

    private static final JokeFinder finder = new RandomJokeFinder();

    public static void main(String[] args){
        String consumerKey = getKey(CONSUMER_KEY);
        String consumerSecret = getKey(CONSUMER_SECRET);
        String accessToken = getKey(ACCESS_TOKEN);
        String accessTokenSecret = getKey(ACCESS_TOKEN_SECRET);

        JokeSender sender = new TweetSender(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        Joke joke = finder.findJoke();
        sender.sendJoke(joke);
        System.out.println("Tweet sent!");
        finder.useJoke(joke);
    }

    private static String getKey(String key){
        return Optional.ofNullable(System.getenv(key))
                .orElseThrow(() -> new IllegalArgumentException(key + " missing"));
    }

}
