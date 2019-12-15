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
            var status = twitter.updateStatus(String.format(QUESTION_FORMAT, joke.getQuestion()));
            StatusUpdate answer = new StatusUpdate(String.format(ANSWER_FORMAT, joke.getAnswer()));
            answer.setInReplyToStatusId(status.getId());
            twitter.updateStatus(answer);

        } catch (TwitterException e) {
            throw new RuntimeException("Couldn't send tweet");
        }
    }
}
