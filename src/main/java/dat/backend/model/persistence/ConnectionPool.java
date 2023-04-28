package dat.backend.model.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {

    private final HikariDataSource ds;
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    private static final String URL = "jdbc:mysql://localhost:3306/fogprojekt";

    public ConnectionPool() {
        this(USER, PASSWORD, URL);
    }

    public ConnectionPool(String user, String password, String url) {
        String deployed = System.getenv("DEPLOYED");
        if (deployed != null) {
            // Prod: hent variabler fra setenv.sh i Tomcats bin folder
            user = System.getenv("JDBC_USER");
            password = System.getenv("JDBC_PASSWORD");
            url = System.getenv("JDBC_CONNECTION_STRING");
        }

        Logger.getLogger("web").log(Level.INFO,
                String.format("Connection Pool created for: (%s, %s, %s)", user, password, url));
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        Logger.getLogger("web").log(Level.INFO, ": get data connection");
        return ds.getConnection();
    }

    public void close() {
        Logger.getLogger("web").log(Level.INFO, "Shutting down connection pool");
        ds.close();
    }
}