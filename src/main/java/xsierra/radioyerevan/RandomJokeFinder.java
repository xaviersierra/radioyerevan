package xsierra.radioyerevan;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

@Service
public class RandomJokeFinder implements JokeFinder {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();

    @Autowired
    private ResourceLoader loader;

    private List<Joke> jokes;

    @PostConstruct
    public void init() throws IOException {
        jokes = mapper.readValue(loader.getResource("classpath:jokes.json").getURL(), new TypeReference<>() {
        });
    }

    @Override
    public Joke findJoke() {
        return jokes.get(random.nextInt(jokes.size()));
    }
}
