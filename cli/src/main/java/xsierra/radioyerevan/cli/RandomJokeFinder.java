package xsierra.radioyerevan.cli;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.function.Function;

public class RandomJokeFinder implements JokeFinder {

    public static final String POSTGRES_URL = System.getenv("POSTGRES_URL");
    public static final String USER = System.getenv("POSTGRES_USER");
    public static final String PASSWORD = System.getenv("POSTGRES_PASSWORD");
    private static final String COUNT_JOKES_SQL = "SELECT COUNT(1) as jokes_count from jokes";
    private final Random random = new Random();

    @Override
    public Joke findJoke() {
        try {
            var count = getJokeCount();
            return getJokeById(random.nextInt(count) + 1);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getJokeCount() throws SQLException {
        return query(COUNT_JOKES_SQL, rs -> {
            try {
                if (rs.next()) {
                    return rs.getInt("jokes_count");
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Joke getJokeById(int id) throws SQLException {
        return query("SELECT question, answer FROM jokes WHERE id = ?", rs -> {
            try {
                if (rs.next()) {
                    Joke joke = new Joke();
                    joke.setAnswer(rs.getString("answer"));
                    joke.setQuestion(rs.getString("question"));
                    return joke;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, id);
    }

    public <T> T query(String sql, Function<ResultSet, T> f, Object... params) throws SQLException {

        try (var connection = DriverManager
                .getConnection(POSTGRES_URL, USER, PASSWORD);
             var ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (var rs = ps.executeQuery()) {
                return f.apply(rs);
            }
        }
    }
}
