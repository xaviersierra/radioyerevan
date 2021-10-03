package xsierra.radioyerevan.jokepublisher.twitter;

public class EnvTwitterConfigurationLoader implements TwitterConfigurationLoader {

    public static final String CONSUMER_KEY = System.getenv("CONSUMER_KEY");
    public static final String CONSUMER_SECRET = System.getenv("CONSUMER_SECRET");
    public static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
    public static final String ACCESS_TOKEN_SECRET = System.getenv("ACCESS_TOKEN_SECRET");

    @Override
    public TwitterConfiguration loadTwitterConfiguration() {
        return ImmutableTwitterConfiguration.builder()
                .consumerKey(CONSUMER_KEY)
                .consumerSecret(CONSUMER_SECRET)
                .accessToken(ACCESS_TOKEN)
                .accessTokenSecret(ACCESS_TOKEN_SECRET)
                .build();
    }
}
