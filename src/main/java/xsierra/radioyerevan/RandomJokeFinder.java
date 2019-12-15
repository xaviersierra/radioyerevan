package xsierra.radioyerevan;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class RandomJokeFinder implements JokeFinder {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();

    private List<Joke> jokes;

    public void init() {

        try (InputStream is = ClassLoader.getSystemResourceAsStream("jokes.json")) {
            if (is == null) {
                throw new IllegalArgumentException("jokes.json not found");
            }
            jokes = mapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Cannot read jokes.json");
        }
    }

    @Override
    public Joke findJoke() {
        if (jokes == null) {
            init();
        }
        return jokes.get(random.nextInt(jokes.size()));
    }
}
