package xsierra.radioyerevan.jokepublisher.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import xsierra.radioyerevan.jokeaccess.Joke;
import xsierra.radioyerevan.jokepublisher.JokeConsumer;

public class TwitterPublisher implements JokeConsumer {

    private final TwitterConfigurationLoader configurationLoader;

    public TwitterPublisher(TwitterConfigurationLoader configurationLoader) {
        this.configurationLoader = configurationLoader;
    }

    @Override
    public void accept(Joke joke) {

        var twitterConfiguration = configurationLoader.loadTwitterConfiguration();
        String consumerKey = twitterConfiguration.getConsumerKey();
        String secretKey = twitterConfiguration.getConsumerSecret();
        String accessToken = twitterConfiguration.getAccessToken();
        String accessTokenSecret = twitterConfiguration.getAccessTokenSecret();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(secretKey)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            Status questionStatus = twitter.updateStatus(String.format(Joke.QUESTION_FORMAT, joke.getQuestion()));
            var formattedAnswer = AnswerFormatter.formatAnswer(joke.getAnswer());
            long lastStatusId = questionStatus.getId();

            for (String partialAnswer : formattedAnswer) {
                StatusUpdate answer = new StatusUpdate(partialAnswer);
                answer.setInReplyToStatusId(lastStatusId);
                var answerStatus = twitter.updateStatus(answer);
                lastStatusId = answerStatus.getId();
            }
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }

    }
}
