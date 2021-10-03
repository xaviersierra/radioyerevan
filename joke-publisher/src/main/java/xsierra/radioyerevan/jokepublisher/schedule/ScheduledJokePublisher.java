package xsierra.radioyerevan.jokepublisher.schedule;

import xsierra.radioyerevan.jokeaccess.Joke;
import xsierra.radioyerevan.jokeaccess.JokeFinder;
import xsierra.radioyerevan.jokepublisher.JokeConsumer;
import xsierra.radioyerevan.jokepublisher.JokePublisher;

import java.util.ArrayList;
import java.util.List;

public class ScheduledJokePublisher implements JokePublisher {

    private final JokeFinder jokeFinder;

    private List<JokeConsumer> subscriptions = new ArrayList<>();

    public ScheduledJokePublisher(JokeFinder jokeFinder) {
        this.jokeFinder = jokeFinder;
    }

    @Override
    public void subscribe(JokeConsumer subscriber) {
        subscriptions.add(subscriber);
    }

    @Override
    public void publishJoke() {
        var joke = jokeFinder.findJoke();
        notify(joke);
        jokeFinder.useJoke(joke);
    }

    private void notify(Joke joke) {
        subscriptions.forEach(subscriber -> subscriber.accept(joke));
    }

}
