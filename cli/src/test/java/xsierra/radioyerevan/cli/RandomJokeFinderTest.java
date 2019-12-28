package xsierra.radioyerevan.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomJokeFinderTest {

    private JokeFinder finder = new RandomJokeFinder();

    @Test
    void findJoke() {
        Joke joke = finder.findJoke();
        assertNotNull(joke);
        assertNotNull(joke.getAnswer());
        assertNotNull(joke.getQuestion());
    }
}