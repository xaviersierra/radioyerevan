package xsierra.radioyerevan;

import xsierra.radioyerevan.printer.ConsoleJokePrinter;
import xsierra.radioyerevan.printer.JokePrinter;

public class RadioYerevanConsoleApplication {

    private static final JokeFinder jokeFinder = new RandomJokeFinder();

    private static final JokePrinter jokePrinter = new ConsoleJokePrinter();

    public static void main(String[] args) {
        var joke = jokeFinder.findJoke();
        jokePrinter.printJoke(joke);
    }
}
