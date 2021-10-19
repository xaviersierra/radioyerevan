package xsierra.radioyerevan.jokepublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xsierra.radioyerevan.jokeaccess.JokeFinder;
import xsierra.radioyerevan.jokepublisher.console.ConsoleSubscriber;
import xsierra.radioyerevan.jokepublisher.schedule.JokeScheduler;
import xsierra.radioyerevan.jokepublisher.twitter.EnvTwitterConfigurationLoader;
import xsierra.radioyerevan.jokepublisher.twitter.TwitterPublisher;
import xsierra.radioyerevan.postgres.DatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.EnvVarDatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.RandomJokeFinder;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final DatabaseConfigurationLoader CONFIGURATION_LOADER = new EnvVarDatabaseConfigurationLoader();
    private static final JokeFinder JOKE_FINDER = new RandomJokeFinder(CONFIGURATION_LOADER);

    private static final boolean TWITTER_PUBLISHER_ENABLED = Boolean.parseBoolean(
            System.getenv("TWITTER_PUBLISHER_ENABLED")
    );

    private static final boolean CONSOLE_PUBLISHER_ENABLED = Boolean.parseBoolean(
            System.getenv("CONSOLE_PUBLISHER_ENABLED")
    );

    private static boolean shutdownSignal = false;

    public static void main(String... args) {

        if (CONSOLE_PUBLISHER_ENABLED) {
            LOG.info("Adding console publisher");
            JokeConsumerRegistry.addJokeConsumer(new ConsoleSubscriber());
        } else {
            LOG.info("Skipping console publisher");
        }

        if (TWITTER_PUBLISHER_ENABLED) {
            LOG.info("Adding twitter publisher");
            JokeConsumerRegistry.addJokeConsumer(new TwitterPublisher(new EnvTwitterConfigurationLoader()));
        } else {
            LOG.info("Skipping twitter publisher");
        }

        JokeConsumerRegistry.registerJokeFinder(JOKE_FINDER);

        try (JokeScheduler ignored = new JokeScheduler()) {
            LOG.info("Joke Publisher running");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOG.info("Gracefully shutting down...");
                shutdownSignal = true;
            }));
            while (!shutdownSignal) {
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            LOG.error("Joke scheduler failed", ex);
        }

    }
}
