package xsierra.radioyerevan.postgres;

import org.immutables.value.Value;

@Value.Immutable
public interface DatabaseConfiguration {

    String getUrl();
    String getUser();
    String getPassword();

}
