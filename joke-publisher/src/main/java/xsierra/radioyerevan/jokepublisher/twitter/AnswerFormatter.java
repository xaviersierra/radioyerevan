package xsierra.radioyerevan.jokepublisher.twitter;

import xsierra.radioyerevan.jokeaccess.Joke;

public class AnswerFormatter {

     public static String[] formatAnswer(String answer) {
        if (answer.length() > (Joke.ANSWER_FORMAT.length() - 2)) {
            return answer.split("\n");
        } else {
            return new String[]{answer};
        }
    }
}
