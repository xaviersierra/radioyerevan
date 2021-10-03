package xsierra.radioyerevan.jokepublisher.console;

import xsierra.radioyerevan.jokeaccess.Joke;
import xsierra.radioyerevan.jokepublisher.JokeConsumer;

public class ConsoleSubscriber implements JokeConsumer {
    @Override
    public void accept(Joke joke) {
        System.out.println(String.format(Joke.QUESTION_FORMAT, joke.getQuestion()));
        System.out.println(String.format(Joke.QUESTION_FORMAT, joke.getAnswer()));
    }
}
