package dat.backend.model.persistence;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TestDatabase {
    protected ConnectionPool connectionPool;
    public TestDatabase() {
        connectionPool = new ConnectionPool(System.getenv("JDBC_USER"), System.getenv("JDBC_PASSWORD"), System.getenv("JDBC_CONNECTION_STRING"));
    }

    public abstract void setUpClass();
    public abstract void setUp();
    @Test
    void testConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        assertTrue(connection.isValid(1));
        assertFalse(connection.isClosed());
        connection.close();
        assertTrue(connection.isClosed());
        assertFalse(connection.isValid(1));
    }
}
