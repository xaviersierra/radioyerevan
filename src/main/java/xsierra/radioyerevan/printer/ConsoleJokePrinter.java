package xsierra.radioyerevan.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsierra.radioyerevan.Joke;

@Service
public class ConsoleJokePrinter implements JokePrinter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleJokePrinter.class);

    private static final String JOKE_FORMAT = "Radio Yerevan were asked: %s\nRadio Yerevan answered: %s";

    @Override
    public void printJoke(Joke joke) {
        LOGGER.info(String.format(JOKE_FORMAT, joke.getQuestion(), joke.getAnswer()));
    }
}
