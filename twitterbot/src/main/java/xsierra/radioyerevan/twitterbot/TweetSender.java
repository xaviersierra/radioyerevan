package xsierra.radioyerevan.twitterbot;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import xsierra.radioyerevan.jokeaccess.Joke;

public class TweetSender implements JokeSender {

    public static final String CONSUMER_KEY = System.getenv("CONSUMER_KEY");
    public static final String CONSUMER_SECRET = System.getenv("CONSUMER_SECRET");
    public static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
    public static final String ACCESS_TOKEN_SECRET = System.getenv("ACCESS_TOKEN_SECRET");

    private static final String QUESTION_FORMAT = "Radio Yerevan were asked: %s";

    @Override
    public void sendJoke(Joke joke) {

        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(CONSUMER_KEY)
                    .setOAuthConsumerSecret(CONSUMER_SECRET)
                    .setOAuthAccessToken(ACCESS_TOKEN)
                    .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

            if (CONSUMER_KEY == null || CONSUMER_KEY.isBlank() ){
                System.out.println("Consumer key is null");
            }
            if (CONSUMER_SECRET == null || CONSUMER_SECRET.isBlank()){
                System.out.println("Consumer secret is null");
            }
            if (ACCESS_TOKEN == null || ACCESS_TOKEN.isBlank()){
                System.out.println("access token is null");
            }
            if (ACCESS_TOKEN_SECRET == null || ACCESS_TOKEN_SECRET.isBlank()){
                System.out.println("access token secret is null");
            }
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            var questionStatus = twitter.updateStatus(String.format(QUESTION_FORMAT, joke.getQuestion()));

            var formattedAnswer = AnswerFormatter.formatAnswer(joke.getAnswer());
            long lastStatusId = questionStatus.getId();

            for (String partialAnswer : formattedAnswer) {
                StatusUpdate answer = new StatusUpdate(partialAnswer);
                answer.setInReplyToStatusId(lastStatusId);
                var answerStatus = twitter.updateStatus(answer);
                lastStatusId = answerStatus.getId();
            }

        } catch (TwitterException e) {
            throw new RuntimeException("Couldn't send tweet");
        }
    }

}
