package xsierra.radioyerevan.twitterbot;

public class AnswerFormatter {

    private static final String ANSWER_FORMAT = "Radio Yerevan answered: %s";

    public static String[] formatAnswer(String answer) {
        if (answer.length() > (ANSWER_FORMAT.length() - 2)) {
            return answer.split("\n");
        } else {
            return new String[]{answer};
        }
    }
}
