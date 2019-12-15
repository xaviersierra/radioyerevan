package xsierra.radioyerevan.cli.printer;

import xsierra.radioyerevan.cli.Joke;

public class ConsoleJokePrinter implements JokePrinter {

    private static final String JOKE_FORMAT = "Radio Yerevan were asked: %s\nRadio Yerevan answered: %s";

    @Override
    public void printJoke(Joke joke) {
        System.out.println(String.format(JOKE_FORMAT, joke.getQuestion(), joke.getAnswer()));
    }
}
