package xsierra.radioyerevan.jokepublisher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xsierra.radioyerevan.jokeaccess.Joke;
import xsierra.radioyerevan.jokeaccess.JokeFinder;
import xsierra.radioyerevan.jokepublisher.schedule.ScheduledJokePublisher;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduledJokePublisherTest {

    private Joke testJoke = new Joke();

    ScheduledJokePublisherTest() throws Exception {
    }

    @DisplayName("Subscribers should receive a joke every time a joke is published")
    @Test
    void testSubscription(){

        AtomicBoolean subscriberCalled = new AtomicBoolean(false);
        AtomicReference<Joke> atomicJoke = new AtomicReference<>(null);
        JokeConsumerRegistry.addJokeConsumer(joke -> {
            subscriberCalled.set(true);
            atomicJoke.set(joke);
        });

        JokeConsumerRegistry.registerJokeFinder(new JokeFinder() {
            @Override
            public Joke findJoke() {
                return testJoke;
            }

            @Override
            public void useJoke(Joke joke) {

            }
        });

        ScheduledJokePublisher jokePublisher = new ScheduledJokePublisher();

        jokePublisher.publishJoke();

        assertTrue(subscriberCalled.get(), "Subscriber should have been called");

        assertSame(testJoke, atomicJoke.get(), "Received joke should be the same as the testJoke");

        JokeConsumerRegistry.clear();
    }

}