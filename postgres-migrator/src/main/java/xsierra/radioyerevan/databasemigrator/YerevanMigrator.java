package xsierra.radioyerevan.databasemigrator;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.postgresql.util.PSQLException;

import java.net.ConnectException;

public class YerevanMigrator {

    public static final String POSTGRES_URL = System.getenv("POSTGRES_URL");
    public static final String USER = System.getenv("POSTGRES_USER");
    public static final String PASSWORD = System.getenv("POSTGRES_PASSWORD");

    public static void main(String[] args) throws InterruptedException {

        Flyway flyway = Flyway.configure().dataSource(POSTGRES_URL, USER, PASSWORD).locations("migrations").load();

        while (true) {
            try {
                flyway.migrate();
                System.out.println("Migration completed successfully");
                break;
            } catch (FlywaySqlException fe) {
                if (fe.getCause() instanceof PSQLException) {
                    PSQLException psqlEx = (PSQLException) fe.getCause();
                    if (psqlEx.getCause() instanceof ConnectException) {
                        System.err.println("Could not connect to database. Retrying in 15 seconds...");
                        Thread.sleep(15000);
                    } else {
                        System.err.println("Database initialization failed, cause: " + psqlEx.getCause());
                        psqlEx.printStackTrace();
                        Thread.sleep(15000);
                    }
                } else {
                    System.err.println("Flyway failed with the following message: " + fe.getMessage());
                    fe.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
