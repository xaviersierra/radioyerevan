package xsierra.radioyerevan.databasemigrator;

import org.flywaydb.core.Flyway;

public class YerevanMigrator {

    public static final String POSTGRES_URL = System.getenv("POSTGRES_URL");
    public static final String USER = System.getenv("POSTGRES_USER");
    public static final String PASSWORD = System.getenv("POSTGRES_PASSWORD");

    public static void main(String[] args){

        Flyway flyway = Flyway.configure().dataSource(POSTGRES_URL, USER, PASSWORD)
                .locations("migrations").load();

        flyway.migrate();
    }
}
