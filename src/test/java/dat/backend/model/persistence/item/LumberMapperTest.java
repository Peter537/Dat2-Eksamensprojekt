package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LumberMapperTest extends TestDatabase {

    @BeforeEach
    public void setUp() {
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
    void testValidGetLumberByType() throws DatabaseException, NotFoundException {
        // Arrange
        LumberType type = LumberTypeFacade.getLumberTypeById(1, connectionPool);

        // Act
        List<Lumber> lumber = LumberFacade.getLumberByType(type, connectionPool);
        assertEquals(2, lumber.size());

        for (Lumber l : lumber) {
            // Assert
            assertNotNull(l);
            assertNotNull(l.getLumberType());
        }
    }

    @Test
    void testInvalidGetLumberByType() throws DatabaseException, NotFoundException {
        // Arrange
        LumberType type = LumberTypeFacade.getLumberTypeById(3, connectionPool);

        // Assert throws exception
        assertTrue(LumberFacade.getLumberByType(type, connectionPool).isEmpty());
        assertEquals(0, LumberFacade.getLumberByType(type, connectionPool).size());
    }

    @Test
    void testValidCreateLumber() throws DatabaseException, NotFoundException {
        // Arrange
        int expectedLength = 200;
        LumberType expectedType = LumberTypeFacade.getLumberTypeById(1, connectionPool);
        int expectedAmount = 1000;
        int expectedPrice = LumberMapper.calcPrice(expectedLength, expectedType.getMeterPrice());

        // Act
        Lumber lumber = LumberFacade.createLumber(expectedLength, expectedType.getId(), expectedAmount, connectionPool);

        // Assert
        assertEquals(expectedLength, lumber.getLength());
        assertEquals(expectedType, lumber.getLumberType());
        assertEquals(expectedPrice, lumber.getPrice());
        assertEquals(expectedAmount, lumber.getAmount());
    }
}