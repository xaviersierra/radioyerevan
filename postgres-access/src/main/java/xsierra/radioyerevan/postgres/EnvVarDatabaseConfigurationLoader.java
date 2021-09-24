package xsierra.radioyerevan.postgres;

public class EnvVarDatabaseConfigurationLoader implements DatabaseConfigurationLoader {

    public static final String POSTGRES_URL = System.getenv("POSTGRES_URL");
    public static final String USER = System.getenv("POSTGRES_USER");
    public static final String PASSWORD = System.getenv("POSTGRES_PASSWORD");

    @Override
    public DatabaseConfiguration loadDatabaseConfiguration() {
        return ImmutableDatabaseConfiguration.builder()
                .url(POSTGRES_URL)
                .user(USER)
                .password(PASSWORD)
                .build();
    }
}
