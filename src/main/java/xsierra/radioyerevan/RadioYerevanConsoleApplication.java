package xsierra.radioyerevan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xsierra.radioyerevan.printer.JokePrinter;

import javax.inject.Inject;

@SpringBootApplication
public class RadioYerevanConsoleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RadioYerevanConsoleApplication.class);

    private static final String JOKE_FORMAT = "Radio Yerevan were asked: %s\nRadio Yerevan answered: %s";

    @Inject
    private JokeFinder jokeFinder;

    @Inject
    private JokePrinter jokePrinter;

    public static void main(String[] args) {
        SpringApplication.run(RadioYerevanConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var joke = jokeFinder.findJoke();
        jokePrinter.printJoke(joke);
    }
}
