package xsierra.radioyerevan.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomJokeFinderTest {

    private RandomJokeFinder finder = new RandomJokeFinder();

    @BeforeEach
    void setUp() throws IOException {
        finder.init();
    }

    @Test
    void findJoke() {
        Joke joke = finder.findJoke();
        assertNotNull(joke);
        assertNotNull(joke.getAnswer());
        assertNotNull(joke.getQuestion());
    }
}