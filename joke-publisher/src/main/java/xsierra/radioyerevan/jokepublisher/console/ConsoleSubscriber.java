package xsierra.radioyerevan.jokepublisher.console;

import xsierra.radioyerevan.jokeaccess.Joke;
import xsierra.radioyerevan.jokepublisher.JokeConsumer;

public class ConsoleSubscriber implements JokeConsumer {
    @Override
    public void accept(Joke joke) {
        System.out.printf((Joke.QUESTION_FORMAT) + "%n", joke.getQuestion());
        System.out.printf((Joke.ANSWER_FORMAT) + "%n", joke.getAnswer());
    }
}
