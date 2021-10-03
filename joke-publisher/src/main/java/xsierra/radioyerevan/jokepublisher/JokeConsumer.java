package xsierra.radioyerevan.jokepublisher;

import xsierra.radioyerevan.jokeaccess.Joke;

public interface JokeConsumer {

    void accept(Joke joke);
}
