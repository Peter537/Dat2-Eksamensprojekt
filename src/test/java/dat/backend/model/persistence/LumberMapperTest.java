package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LumberMapperTest {

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(System.getenv("JDBC_USER"), System.getenv("JDBC_PASSWORD"), System.getenv("JDBC_CONNECTION_STRING"));

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE IF NOT EXISTS fogcarport_test;");

                // Create user table. Add your own tables here
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.lumber LIKE fogcarport.lumber;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.lumbertype LIKE fogcarport.lumbertype;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.type LIKE fogcarport.type;");
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
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM lumber");
                stmt.execute("DELETE FROM lumbertype");
                stmt.execute("DELETE FROM type");
                stmt.execute("ALTER TABLE lumber AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE lumbertype AUTO_INCREMENT = 1;");

                // Insert a few lumbers - insert rows into your own tables here
                stmt.execute("INSERT INTO type (type, displayname) " +
                        "VALUES ('RAFTER', 'Spærtræ'), ('POLE', 'Stolpe'), ('PLASTIC_ROOF', 'Plastic tag')");
                stmt.execute("INSERT INTO lumbertype (thickness, width, type, meter_price) " +
                        "VALUES (97, 97, 'POLE', 60), (45, 195, 'RAFTER', 48), (45, 245, 'RAFTER', 82)");
                stmt.execute("INSERT INTO lumber (length, type, amount)" +
                        " VALUES (180, 1, 1000), (240, 1, 1000), (360, 2, 1000)");
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
    void testValidGetLumberById() throws DatabaseException {
        // Arrange
        int id = 1;
        int expectedLength = 180;
        LumberType expectedType = LumbertypeMapper.getLumbertypeById(1, connectionPool).orElse(null);
        int expectedAmount = 1000;
        assert expectedType != null;
        int expectedPrice = Math.round(expectedType.getMeterPrice() * expectedLength);

        // Act
        Lumber lumber = LumberMapper.getLumberById(id, connectionPool).orElse(null);

        // Assert
        assertNotNull(lumber);
        assertEquals(expectedLength, lumber.getLength());
        assertEquals(expectedType, lumber.getLumberType());
        assertEquals(expectedPrice, lumber.getPrice());
        assertEquals(expectedAmount, lumber.getAmount());
    }

    @Test
    void testInvalidGetLumberById() {
        // Arrange
        int id = 100;

        // Assert throws exception
        assertThrows(DatabaseException.class, () -> LumberMapper.getLumberById(id, connectionPool));
    }

    @Test
    void testValidGetLumberByType() throws DatabaseException {
        // Arrange
        LumberType type = LumbertypeMapper.getLumbertypeById(1, connectionPool).orElse(null);
        assert type != null;

        // Act
        ArrayList<Lumber> lumber = LumberMapper.getLumberByType(type, connectionPool).orElse(null);
        assert lumber != null;
        assertEquals(2, lumber.size());

        for (Lumber l : lumber) {
            // Assert
            assertNotNull(l);
            assertNotNull(l.getLumberType());
        }
    }

    @Test
    void testInvalidGetLumberByType() throws DatabaseException {
        // Arrange
        LumberType type = LumbertypeMapper.getLumbertypeById(3, connectionPool).orElse(null);
        assert type != null;

        // Assert throws exception
        assertThrows(DatabaseException.class, () -> LumberMapper.getLumberByType(type, connectionPool));
    }

    @Test
    void testValidGetLumberByLength() throws DatabaseException {
        // Arrange
        LumberType type = LumbertypeMapper.getLumbertypeById(1, connectionPool).orElse(null);
        assert type != null;
        int expectedLength = 180;
        int expectedAmount = 1000;
        int expectedPrice = Math.round(type.getMeterPrice() * expectedLength);

        // Act
        ArrayList<Lumber> lumber = LumberMapper.getLumberByLength(expectedLength, connectionPool).orElse(null);
        assert lumber != null;
        assertEquals(1, lumber.size());

        for (Lumber l : lumber) {
            // Assert
            assertNotNull(l);
            assertEquals(expectedLength, l.getLength());
            assertEquals(type, l.getLumberType());
            assertEquals(expectedPrice, l.getPrice());
            assertEquals(expectedAmount, l.getAmount());
        }
    }

    @Test
    void testInvalidGetLumberByLength() {
        // Arrange
        int length = 100;

        // Assert throws exception
        assertThrows(DatabaseException.class, () -> LumberMapper.getLumberByLength(length, connectionPool));
    }

    @Test
    void testValidGetAllLumber() throws DatabaseException {
        // Arrange
        int expectedAmount = 3;

        // Act
        ArrayList<Lumber> lumber = LumberMapper.getAllLumber(connectionPool).orElse(null);
        assert lumber != null;
        assertEquals(expectedAmount, lumber.size());

        for (Lumber l : lumber) {
            // Assert
            assertNotNull(l);
            assertNotNull(l.getLumberType());
        }
    }

    @Test
    void testCreateLumber() throws DatabaseException {
        // Arrange
        int expectedLength = 200;
        LumberType expectedType = LumbertypeMapper.getLumbertypeById(1, connectionPool).orElse(null);
        assert expectedType != null;
        int expectedAmount = 1000;
        int expectedPrice = Math.round(expectedType.getMeterPrice() * expectedLength);

        // Act
        Lumber lumber = LumberMapper.createLumber(expectedLength, expectedType.getId(), expectedAmount, connectionPool).orElse(null);

        // Assert
        assertNotNull(lumber);
        assertEquals(expectedLength, lumber.getLength());
        assertEquals(expectedType, lumber.getLumberType());
        assertEquals(expectedPrice, lumber.getPrice());
        assertEquals(expectedAmount, lumber.getAmount());
    }
}
