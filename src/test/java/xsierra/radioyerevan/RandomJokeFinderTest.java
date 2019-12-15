package xsierra.radioyerevan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RandomJokeFinderTest {

    @Autowired
    private RandomJokeFinder finder;

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