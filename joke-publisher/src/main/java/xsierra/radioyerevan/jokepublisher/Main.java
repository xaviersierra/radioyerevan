package xsierra.radioyerevan.jokepublisher;

import xsierra.radioyerevan.jokeaccess.JokeFinder;
import xsierra.radioyerevan.jokepublisher.console.ConsoleSubscriber;
import xsierra.radioyerevan.jokepublisher.schedule.JokeScheduler;
import xsierra.radioyerevan.jokepublisher.schedule.ScheduledJokePublisher;
import xsierra.radioyerevan.jokepublisher.twitter.EnvTwitterConfigurationLoader;
import xsierra.radioyerevan.jokepublisher.twitter.TwitterPublisher;
import xsierra.radioyerevan.postgres.DatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.EnvVarDatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.RandomJokeFinder;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final DatabaseConfigurationLoader CONFIGURATION_LOADER = new EnvVarDatabaseConfigurationLoader();
    private static final JokeFinder JOKE_FINDER = new RandomJokeFinder(CONFIGURATION_LOADER);
    private static final JokePublisher JOKE_PUBLISHER = new ScheduledJokePublisher(JOKE_FINDER);

    private static final boolean TWITTER_PUBLISHER_ENABLED = Boolean.parseBoolean(
            System.getenv("TWITTER_PUBLISHER_ENABLED")
    );

    private static final boolean CONSOLE_PUBLISHER_ENABLED = Boolean.parseBoolean(
            System.getenv("CONSOLE_PUBLISHER_ENABLED")
    );

    private static boolean shutdownSignal = false;

    public static void main(String... args) {

        List<JokeConsumer> consumers = new ArrayList<>();

        if (CONSOLE_PUBLISHER_ENABLED) {
            consumers.add(new ConsoleSubscriber());
        }

        if (TWITTER_PUBLISHER_ENABLED) {
            consumers.add(new TwitterPublisher(new EnvTwitterConfigurationLoader()));
        }

        JokeChannel jokeChannel = new JokeChannel(JOKE_PUBLISHER, consumers);
        jokeChannel.startChannel();

        try (JokeScheduler ignored = new JokeScheduler(JOKE_PUBLISHER)) {
            System.out.println("Joke Publisher running");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Gracefully shutting down...");
                shutdownSignal = true;
            }));
            while (!shutdownSignal) {
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            System.err.println("Joke scheduler failed");
            ex.printStackTrace();
        }

    }
}
