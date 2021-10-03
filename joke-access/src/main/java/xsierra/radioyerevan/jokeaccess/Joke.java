package xsierra.radioyerevan.jokeaccess;

public class Joke {

    public static String QUESTION_FORMAT = "Radio Yerevan were asked: %s";
    public static String ANSWER_FORMAT = "Radio Yerevan answered: %s";

    private int id;

    private String question;

    private String answer;

    private int timesUsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }
}
