package dat.backend.model.persistence;

import dat.backend.model.entities.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ZipNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ZipMapperTest {

    private final static String USER = "root";
    private final static String PASSWORD = "123";
    private final static String URL = "jdbc:mysql://localhost:3306/fogcarport_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE IF NOT EXISTS fogcarport_test;");

                // TODO: Create user table. Add your own tables here
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.zip LIKE fogcarport.zip;");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // TODO: Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM zip");
                stmt.execute("ALTER TABLE department AUTO_INCREMENT = 1;");

                // TODO: Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO zip (zipcode, city_name) VALUES (2800, 'Lyngby'), (1400, 'København'), (3400, 'Hillerød')");
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

    @Test
    void testValidGetZipByZipCode() throws ZipNotFoundException, DatabaseException {
        Zip zip = ZipFacade.getZipByZipCode(2800, connectionPool);
        assertNotNull(zip);
        assertEquals(2800, zip.getZipCode());
        assertEquals("Lyngby", zip.getCityName());
    }

    @Test
    void testInvalidGetZipByZipCode() {
        assertThrows(ZipNotFoundException.class, () -> ZipFacade.getZipByZipCode(9999, connectionPool));
    }
}