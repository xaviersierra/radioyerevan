package xsierra.radioyerevan.jokepublisher.twitter;

import xsierra.radioyerevan.jokeaccess.Joke;

import java.util.Arrays;

public class AnswerFormatter {

     public static String[] formatAnswer(String answer) {
        if (answer.length() > (Joke.ANSWER_FORMAT.length() - 2)) {
            return Arrays.stream(answer.split("\n")).filter(a -> !a.isEmpty()).toArray(String[]::new);
        } else {
            return new String[]{answer};
        }
    }
}
