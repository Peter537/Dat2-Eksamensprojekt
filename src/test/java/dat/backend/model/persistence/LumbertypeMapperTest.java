package dat.backend.model.persistence;

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

public class LumbertypeMapperTest {
    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(System.getenv("JDBC_USER"), System.getenv("JDBC_PASSWORD"), System.getenv("JDBC_CONNECTION_STRING"));

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE IF NOT EXISTS fogcarport_test;");

                // Create user table. Add your own tables here
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
                stmt.execute("DELETE FROM lumbertype");
                stmt.execute("DELETE FROM type");
                stmt.execute("ALTER TABLE lumbertype AUTO_INCREMENT = 1;");

                // Insert a few lumbers - insert rows into your own tables here
                stmt.execute("INSERT INTO type (type, displayname) " +
                        "VALUES ('RAFTER', 'Spærtræ'), ('POLE', 'Stolpe'), ('PLASTIC_ROOF', 'Plastic tag')");
                stmt.execute("INSERT INTO lumbertype (thickness, width, type, meter_price) " +
                        "VALUES (97, 97, 'POLE', 60), (45, 195, 'RAFTER', 48), (45, 245, 'RAFTER', 82)");
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
    void testValidGetLumbertypeById() throws DatabaseException {
        LumberType lumberType = LumbertypeMapper.getLumbertypeById(1, connectionPool).orElse(null);
        assert lumberType != null;
        assertEquals(97, lumberType.getThickness());
        assertEquals(97, lumberType.getWidth());
        assertEquals("POLE", lumberType.getType());
        assertEquals(60, lumberType.getMeterPrice());
    }

    @Test
    void testInvalidGetLumbertypeById() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> LumbertypeMapper.getLumbertypeById(0, connectionPool));
    }

    @Test
    void testValidGetLumbertypeByType() throws DatabaseException {
        ArrayList<LumberType> lumberType = LumbertypeMapper.getLumbertypeByType("POLE", connectionPool).orElse(null);

        assert lumberType != null;
        for (LumberType lt : lumberType) {
            assertEquals(97, lt.getThickness());
            assertEquals(97, lt.getWidth());
            assertEquals("POLE", lt.getType());
            assertEquals(60, lt.getMeterPrice());
        }
    }

    @Test
    void testInvalidGetLumbertypeByType() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> LumbertypeMapper.getLumbertypeByType("INVALID", connectionPool));
    }
}
