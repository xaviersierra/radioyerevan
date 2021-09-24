package xsierra.radioyerevan.cli;

import xsierra.radioyerevan.cli.printer.ConsoleJokePrinter;
import xsierra.radioyerevan.cli.printer.JokePrinter;
import xsierra.radioyerevan.jokeaccess.JokeFinder;
import xsierra.radioyerevan.postgres.DatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.EnvVarDatabaseConfigurationLoader;
import xsierra.radioyerevan.postgres.RandomJokeFinder;

public class RadioYerevanConsoleApplication {

    private static final DatabaseConfigurationLoader configurationLoader = new EnvVarDatabaseConfigurationLoader();

    private static final JokeFinder jokeFinder = new RandomJokeFinder(configurationLoader);

    private static final JokePrinter jokePrinter = new ConsoleJokePrinter();

    public static void main(String[] args) {
        var joke = jokeFinder.findJoke();
        jokePrinter.printJoke(joke);
    }
}
