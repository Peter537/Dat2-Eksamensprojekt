package dat.backend.model.persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestDatabase {
    protected ConnectionPool connectionPool;
    public TestDatabase() {
        connectionPool = new ConnectionPool(System.getenv("JDBC_USER"), System.getenv("JDBC_PASSWORD"), System.getenv("JDBC_CONNECTION_STRING"));
    }

    public abstract void setUp();

    @BeforeAll
    public void setUpClass() {
        connectionPool.getDataSource().close();
        connectionPool = null;
        connectionPool = new ConnectionPool(System.getenv("JDBC_USER"), System.getenv("JDBC_PASSWORD"), System.getenv("JDBC_CONNECTION_STRING"));

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE IF NOT EXISTS fogcarport_test;");

                // Create user table. Add your own tables here
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.lumber LIKE fogcarport.lumber;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.lumbertype LIKE fogcarport.lumbertype;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.type LIKE fogcarport.type;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.carport_order LIKE fogcarport.carport_order;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.customer LIKE fogcarport.customer;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.department LIKE fogcarport.department;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.employee LIKE fogcarport.employee;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.orderstatus LIKE fogcarport.orderstatus;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.position LIKE fogcarport.position;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.roof LIKE fogcarport.roof;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.zip LIKE fogcarport.zip;");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

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

    @AfterAll
    void closeConnections() {
        connectionPool.close();
    }
}
