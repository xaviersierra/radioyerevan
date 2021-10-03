package xsierra.radioyerevan.twitterbot;

import xsierra.radioyerevan.jokeaccess.Joke;
import xsierra.radioyerevan.jokeaccess.JokeFinder;
import xsierra.radioyerevan.postgres.DatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.EnvVarDatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.RandomJokeFinder;

public class TwitterBotApplication {

    private static final DatabaseConfigurationLoader configurationLoader = new EnvVarDatabaseConfigurationLoader();

    private static final JokeFinder finder = new RandomJokeFinder(configurationLoader);

    public static void main(String[] args){
        JokeSender sender = new TweetSender();
        Joke joke = finder.findJoke();
        sender.sendJoke(joke);
        System.out.println("Tweet sent!");
        finder.useJoke(joke);
    }

}
