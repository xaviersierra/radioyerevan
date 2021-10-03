package xsierra.radioyerevan.jokepublisher.twitter;

import org.immutables.value.Value;

@Value.Immutable
public interface TwitterConfiguration {

    String getConsumerKey();

    String getConsumerSecret();

    String getAccessToken();

    String getAccessTokenSecret();
}
