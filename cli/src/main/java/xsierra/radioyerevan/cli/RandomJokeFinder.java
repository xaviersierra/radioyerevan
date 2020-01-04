package xsierra.radioyerevan.cli;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.function.Function;

public class RandomJokeFinder implements JokeFinder {

    public static final String POSTGRES_URL = System.getenv("POSTGRES_URL");
    public static final String USER = System.getenv("POSTGRES_USER");
    public static final String PASSWORD = System.getenv("POSTGRES_PASSWORD");
    private static final String COUNT_JOKES_SQL = "SELECT COUNT(1) as jokes_count FROM jokes";
    private static final String COUNT_JOKES_BY_TIMES_USED_SQL = "SELECT COUNT(1) as jokes_count " +
            "FROM jokes WHERE times_used = ?";
    private static final String INCREASE_JOKE_SQL = "UPDATE jokes SET times_used = ? WHERE times_used = ? AND id = ?";
    private static final String JOKES_BY_ID_SQL = "SELECT id, question, answer, times_used FROM jokes WHERE id = ?";
    private static final String JOKE_MAX_USED = "SELECT MAX(times_used) as times_used FROM jokes;";
    private static final String JOKE_BY_OFFSET = "SELECT id, question, answer, times_used FROM jokes " +
            "WHERE times_used < ? LIMIT 1 OFFSET ?";

    private final Random random = new Random();

    @Override
    public Joke findJoke() {
        try {
            int allJokesCount = getJokeCount();
            int mostUsedTimes = getMostUsedJokes();
            int mostUsingJokes = getJokeCount(mostUsedTimes);

            if (allJokesCount == mostUsingJokes) {
                return getRandomJoke(mostUsedTimes + 1, allJokesCount);
            } else {
                return getRandomJoke(mostUsedTimes, (allJokesCount - mostUsingJokes));
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void useJoke(Joke joke) {

        int newTimesUsed = joke.getTimesUsed() + 1;

        try {
            int updated = update(INCREASE_JOKE_SQL, newTimesUsed, joke.getTimesUsed(), joke.getId());
            if (updated == 0) {
                throw new RuntimeException("Joke uses not increased, some other transaction already updated the joke");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public int getJokeCount(int timesUsed) throws SQLException {
        return query(COUNT_JOKES_BY_TIMES_USED_SQL, rs -> {
            try {
                if (rs.next()) {
                    return rs.getInt("jokes_count");
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, timesUsed);
    }

    public int getMostUsedJokes() throws SQLException {
        return query(JOKE_MAX_USED, rs -> {
            try {
                if (rs.next()) {
                    return rs.getInt("times_used");
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Joke getJokeById(int id) throws SQLException {
        return query(JOKES_BY_ID_SQL, rs -> {
            try {
                if (rs.next()) {
                    Joke joke = new Joke();
                    joke.setId(rs.getInt("id"));
                    joke.setAnswer(rs.getString("answer"));
                    joke.setQuestion(rs.getString("question"));
                    joke.setTimesUsed(rs.getInt("times_used"));
                    return joke;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, id);
    }

    public Joke getRandomJoke(int topUsedJokes, int universeCount) throws SQLException {
        int offset = random.nextInt(universeCount);
        return query(JOKE_BY_OFFSET, rs -> {
            try {
                if (rs.next()) {
                    Joke joke = new Joke();
                    joke.setId(rs.getInt("id"));
                    joke.setAnswer(rs.getString("answer"));
                    joke.setQuestion(rs.getString("question"));
                    joke.setTimesUsed(rs.getInt("times_used"));
                    return joke;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, topUsedJokes, offset);
    }

    public <T> T query(String sql, Function<ResultSet, T> f, Object... params) throws SQLException {

        return prepareStatement(sql, ps -> {
            try (var rs = ps.executeQuery()) {
                return f.apply(rs);
            } catch (SQLException sqEx) {
                throw new RuntimeException(sqEx);
            }
        }, params);

    }

    public int update(String sql, Object... params) throws SQLException {

        return prepareStatement(sql, preparedStatement -> {
            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, params);

    }

    public <T> T prepareStatement(String sql, Function<PreparedStatement, T> f, Object... params) throws SQLException {
        try (var connection = DriverManager
                .getConnection(POSTGRES_URL, USER, PASSWORD);
             var ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try {
                T result = f.apply(ps);
                connection.commit();
                return result;
            } catch (SQLException ex){
                connection.rollback();
                throw ex;
            }
        }
    }
}
