package dat.backend.model.persistence.item;

import dat.backend.model.entities.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoofMapperTest extends TestDatabase {

    @BeforeEach
    public void setUp() {
        try (Connection testConnection = super.connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM roof");
                stmt.execute("DELETE FROM type");
                stmt.execute("ALTER TABLE roof AUTO_INCREMENT = 1;");

                // Insert a few lumbers - insert rows into your own tables here
                stmt.execute("INSERT INTO type (type, displayname) " +
                        "VALUES ('PLASTIC_ROOF', 'Plastic tag'), ('TILED_ROOF', 'Tegl tag')");
                stmt.execute("INSERT INTO roof (squaremeter_price, type) " +
                        "VALUES (100, 'PLASTIC_ROOF'), (200, 'TILED_ROOF')");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testValidCreateRoof() throws DatabaseException {
        // Arrange
        int expectedId = 3;
        String expectedType = "PLASTIC_ROOF";
        float expectedPrice = 100;

        // Act
        Roof newRoof = RoofMapper.createRoof(expectedPrice, expectedType, super.connectionPool).orElse(null);

        // Assert
        assertNotNull(newRoof);
        assertEquals(expectedId, newRoof.getId());
        assertEquals(expectedType, newRoof.getType());
        assertEquals(expectedPrice, newRoof.getSquareMeterPrice());
    }

    @Test
    void testValidCreateRoofWithRoof() throws DatabaseException {
        // Arrange
        int expectedId = 3;
        String expectedType = "PLASTIC_ROOF";
        float expectedPrice = 100;
        Roof roof = new Roof(expectedId, expectedPrice, expectedType);

        // Act
        Roof newRoof = RoofMapper.createRoof(roof, super.connectionPool).orElse(null);

        // Assert
        assertNotNull(newRoof);
        assertEquals(expectedId, newRoof.getId());
        assertEquals(expectedType, newRoof.getType());
        assertEquals(expectedPrice, newRoof.getSquareMeterPrice());
    }

    @Test
    void testValidGetAllRoofs() throws DatabaseException {
        // Arrange
        int expectedSize = 2;
        // Act
        ArrayList<Roof> roof = RoofMapper.getAllRoofs(super.connectionPool).orElse(null);

        // Assert
        assertNotNull(roof);
        assertEquals(expectedSize, roof.size());
    }

    @Test
    void testValidGetRoofById() throws DatabaseException {
        // Arrange
        int expectedId = 1;
        String expectedType = "PLASTIC_ROOF";
        float expectedPrice = 100;

        // Act
        Roof roof = RoofMapper.getRoofById(expectedId, super.connectionPool).orElse(null);

        // Assert
        assertNotNull(roof);
        assertEquals(expectedId, roof.getId());
        assertEquals(expectedType, roof.getType());
        assertEquals(expectedPrice, roof.getSquareMeterPrice());
    }

    @Test
    void testInvalidGetRoofById() {
        assertThrows(DatabaseException.class, () -> RoofMapper.getRoofById(3, super.connectionPool));
    }

    @Test
    void testValidGetRoofByType() throws DatabaseException {
        // Arrange
        int expectedSize = 1;

        // Act
        ArrayList<Roof> roof = RoofMapper.getRoofByType("PLASTIC_ROOF", super.connectionPool).orElse(null);

        // Assert
        assertNotNull(roof);
        assertEquals(expectedSize, roof.size());
    }

    @Test
    void testInvalidGetRoofByType() {
        assertThrows(DatabaseException.class, () -> RoofMapper.getRoofByType("TEST", super.connectionPool));
    }
}
