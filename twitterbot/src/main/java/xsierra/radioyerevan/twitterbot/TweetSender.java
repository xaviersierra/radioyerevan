package xsierra.radioyerevan.twitterbot;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import xsierra.radioyerevan.cli.Joke;

public class TweetSender implements JokeSender {

    private static final String QUESTION_FORMAT = "Radio Yerevan were asked: %s";
    private static final String ANSWER_FORMAT = "Radio Yerevan answered: %s";

    private String apiKey;
    private String apiSecret;
    private String accessToken;
    private String accessTokenSecret;

    public TweetSender(String apiKey, String apiSecret, String accessToken, String accessTokenSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    @Override
    public void sendJoke(Joke joke) {

        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(apiKey)
                    .setOAuthConsumerSecret(apiSecret)
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret);

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            var questionStatus = twitter.updateStatus(String.format(QUESTION_FORMAT, joke.getQuestion()));

            var formattedAnswer = AnswerFormatter.formatAnswer(joke.getAnswer());
            long lastStatusId = questionStatus.getId();

            for(String partialAnswer: formattedAnswer){
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
