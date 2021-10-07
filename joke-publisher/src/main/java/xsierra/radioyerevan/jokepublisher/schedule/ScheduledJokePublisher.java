package xsierra.radioyerevan.jokepublisher.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import xsierra.radioyerevan.jokeaccess.Joke;
import xsierra.radioyerevan.jokeaccess.JokeFinder;
import xsierra.radioyerevan.jokepublisher.JokeConsumer;
import xsierra.radioyerevan.jokepublisher.JokeConsumerRegistry;
import xsierra.radioyerevan.jokepublisher.JokePublisher;

import java.util.List;

public class ScheduledJokePublisher implements JokePublisher, Job {

    private final JokeFinder jokeFinder;

    private final List<JokeConsumer> subscriptions;

    public ScheduledJokePublisher() {
        this.jokeFinder = JokeConsumerRegistry.getJokeFinder();
        this.subscriptions = JokeConsumerRegistry.getConsumers();
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

    @Override
    public void execute(JobExecutionContext context) {
        this.publishJoke();
    }
}
