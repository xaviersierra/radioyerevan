package xsierra.radioyerevan.cli;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class RandomJokeFinderTest {

    private RandomJokeFinder finder = new RandomJokeFinder();

    @Test
    void findJoke_shouldNotFail() {
        Joke joke = finder.findJoke();
        assertNotNull(joke);
        assertNotNull(joke.getAnswer());
        assertNotNull(joke.getQuestion());
    }

    @Test
    void findJoke_shouldNotRepeatAJokeUntilAllOfThenAreUsed() throws SQLException {
        var allJokesCount = finder.getJokeCount();

        Set<Integer> usedJokes = new HashSet<>();

        for (int i = 0; i < allJokesCount; i++){
            var joke = finder.findJoke();
            Integer jokeId = joke.getId();
            if (usedJokes.contains(jokeId)){
                fail("Duplicate joke returned " + jokeId);
            } else {
                finder.useJoke(joke);
                usedJokes.add(jokeId);
            }
        }

    }

}