package xsierra.radioyerevan.postgres;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.Test;
import xsierra.radioyerevan.jokeaccess.Joke;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class RandomJokeFinderTest {

    private final static InMemoryDatabaseConfigurationLoader CONFIGURATION_LOADER
            = new InMemoryDatabaseConfigurationLoader();

    private void withCleanDatabase(Runnable taks) {
        var databaseConfiguration = CONFIGURATION_LOADER.loadDatabaseConfiguration();
        try (var connection = DriverManager
                .getConnection(
                        databaseConfiguration.getUrl(),
                        databaseConfiguration.getUser(),
                        databaseConfiguration.getPassword()
                );
             InputStream schemaInputStream = ClassLoader.getSystemResourceAsStream("jokes.schema.sql");
             InputStreamReader schemaReader = new InputStreamReader(Objects.requireNonNull(schemaInputStream));
             InputStream jokesInputStream = ClassLoader.getSystemResourceAsStream("jokes.sql");
             InputStreamReader jokerReader = new InputStreamReader(Objects.requireNonNull(jokesInputStream))
        ) {
            RunScript.execute(connection, schemaReader);
            RunScript.execute(connection, jokerReader);
            taks.run();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RandomJokeFinder finder = new RandomJokeFinder(CONFIGURATION_LOADER);

    @Test
    void findJoke_shouldNotFail() {
        withCleanDatabase(() -> {
            Joke joke = finder.findJoke();
            assertNotNull(joke);
            assertNotNull(joke.getAnswer());
            assertNotNull(joke.getQuestion());
        });
    }

    @Test
    void findJoke_shouldNotRepeatAJokeUntilAllOfThenAreUsed() {

        withCleanDatabase(() -> {

            int allJokesCount = 0;
            try {
                allJokesCount = finder.getJokeCount();
            } catch (SQLException e) {
                fail("Reading joke count failed", e);
            }

            Set<Integer> usedJokes = new HashSet<>();

            for (int i = 0; i < allJokesCount; i++) {
                var joke = finder.findJoke();
                Integer jokeId = joke.getId();
                if (usedJokes.contains(jokeId)) {
                    fail("Duplicate joke returned " + jokeId);
                } else {
                    finder.useJoke(joke);
                    usedJokes.add(jokeId);
                }
            }
        });

    }

}