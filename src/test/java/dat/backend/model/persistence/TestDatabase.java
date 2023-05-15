package dat.backend.model.persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestDatabase {

    private static boolean shouldClearDatabase = true;
    protected ConnectionPool connectionPool;

    public TestDatabase() {
        this.connectionPool = new ConnectionPool();
    }

    @BeforeAll
    public void setUpClass() {
        this.connectionPool.getDataSource().close();
        this.connectionPool = null;
        this.connectionPool = new ConnectionPool();
        try (Connection testConnection = this.connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                if (shouldClearDatabase) {
                    clearDB(stmt);
                }

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

    public abstract void setUp();

    @Test
    void testConnection() throws SQLException {
        Connection connection = this.connectionPool.getConnection();
        assertNotNull(connection);
        assertTrue(connection.isValid(1));
        assertFalse(connection.isClosed());
        connection.close();
        assertTrue(connection.isClosed());
        assertFalse(connection.isValid(1));
    }

    @AfterAll
    void closeConnectionPool() {
        this.connectionPool.close();
    }

    private void clearDB(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT CONCAT('DROP TABLE ',table_schema,'.',TABLE_NAME, ';') \n" +
                "    FROM INFORMATION_SCHEMA.TABLES WHERE table_schema IN ('fogcarport_test');");
        ArrayList<String> dropTables = new ArrayList<>();
        while (rs.next()) {
            String truncateTables = rs.getString(1);
            truncateTables = truncateTables.replace("DROP TABLE", "DROP TABLE IF EXISTS");
            dropTables.add(truncateTables);
        }

        for (String dropTable : dropTables) {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0;");
            stmt.execute(dropTable);
            stmt.execute("SET FOREIGN_KEY_CHECKS=1;");
        }

        shouldClearDatabase = false;
    }
}