package xsierra.radioyerevan.jokepublisher;

import xsierra.radioyerevan.jokeaccess.JokeFinder;

import java.util.ArrayList;
import java.util.List;

public class JokeConsumerRegistry {

    private static final List<JokeConsumer> JOKE_CONSUMERS = new ArrayList<>();

    private static JokePublisher jokePublisher;

    private static JokeFinder jokeFinder;

    public static void addJokeConsumer(JokeConsumer jokeConsumer) {
        JOKE_CONSUMERS.add(jokeConsumer);
    }

    public static void registerJokePublisher(JokePublisher jokePublisher) {
        JokeConsumerRegistry.jokePublisher = jokePublisher;
    }

    public static void registerJokeFinder(JokeFinder jokeFinder) {
        JokeConsumerRegistry.jokeFinder = jokeFinder;
    }

    public static List<JokeConsumer> getConsumers() {
        return JOKE_CONSUMERS;
    }

    public static JokePublisher getJokePublisher() {
        if (jokePublisher == null) {
            throw new IllegalStateException("Joke Publisher haven't been registered");
        }
        return jokePublisher;
    }

    public static JokeFinder getJokeFinder(){
        if (jokeFinder == null){
            throw new IllegalStateException("Joke finder haven't been registered");
        }
        return jokeFinder;
    }

    public static void clear(){
        JOKE_CONSUMERS.clear();
        jokeFinder = null;
        jokePublisher = null;
    }

}
