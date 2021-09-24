package xsierra.radioyerevan.postgres;

public class InMemoryDatabaseConfigurationLoader implements DatabaseConfigurationLoader {

    @Override
    public DatabaseConfiguration loadDatabaseConfiguration() {
        return ImmutableDatabaseConfiguration.builder()
                .url("jdbc:h2:mem:jokes;MODE=PostgreSQL")
                .user("")
                .password("")
                .build();
    }
}
