package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LumberTypeMapperTest extends TestDatabase {

    @BeforeEach
    public void setUp() {
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
    void testValidGetLumberTypeById() throws DatabaseException, NotFoundException {
        LumberType lumberType = LumberTypeFacade.getLumberTypeById(1, connectionPool);
        assertEquals(97, lumberType.getThickness());
        assertEquals(97, lumberType.getWidth());
        assertEquals("POLE", lumberType.getType());
        assertEquals(60, lumberType.getMeterPrice());
    }

    @Test
    void testInvalidGetLumberTypeById() {
        assertThrows(NotFoundException.class, () -> LumberTypeFacade.getLumberTypeById(0, connectionPool));
    }

    @Test
    void testValidGetLumberTypeByType() throws DatabaseException {
        List<LumberType> lumberType = LumberTypeFacade.getLumberTypeByType("POLE", connectionPool);
        assertFalse(lumberType.isEmpty());
        for (LumberType lt : lumberType) {
            assertEquals(97, lt.getThickness());
            assertEquals(97, lt.getWidth());
            assertEquals("POLE", lt.getType());
            assertEquals(60, lt.getMeterPrice());
        }

        List<LumberType> lumberType2 = LumberTypeFacade.getLumberTypeByType("RAFTER", connectionPool);
        assertFalse(lumberType2.isEmpty());
        assertEquals(45, lumberType2.get(0).getThickness());
        assertEquals(195, lumberType2.get(0).getWidth());
        assertEquals("RAFTER", lumberType2.get(0).getType());
        assertEquals(48, lumberType2.get(0).getMeterPrice());
    }

    @Test
    void testInvalidGetLumberTypeByType() throws DatabaseException {
        assertTrue(LumberTypeFacade.getLumberTypeByType("INVALID", connectionPool).isEmpty());
        assertEquals(0, LumberTypeFacade.getLumberTypeByType("INVALID", connectionPool).size());
    }

    @Test
    void testValidCreateLumberType() throws DatabaseException, ValidationException {
        LumberType lumberType = LumberTypeFacade.createLumberType(55, 195, 48, "RAFTER", connectionPool);
        assertEquals(55, lumberType.getThickness());
        assertEquals(195, lumberType.getWidth());
        assertEquals("RAFTER", lumberType.getType());
        assertEquals(48, lumberType.getMeterPrice());
    }

    @Test
    void testValidCreateLumberTypeAlreadyExists() throws DatabaseException, ValidationException {
        LumberType lumberType = LumberTypeFacade.createLumberType(97, 97, 60, "POLE", connectionPool);
        assertEquals(97, lumberType.getThickness());
        assertEquals(97, lumberType.getWidth());
        assertEquals("POLE", lumberType.getType());
        assertEquals(60, lumberType.getMeterPrice());
    }

    @Test
    void testInvalidCreateLumberTypeNegativeThickness() {
        assertThrows(ValidationException.class, () -> LumberTypeFacade.createLumberType(-1, 97, 60, "POLE", connectionPool));
    }

    @Test
    void testInvalidCreateLumberTypeNegativeWidth() {
        assertThrows(ValidationException.class, () -> LumberTypeFacade.createLumberType(97, -1, 60, "POLE", connectionPool));
    }

    @Test
    void testInvalidCreateLumberTypeNegativeMeterPrice() {
        assertThrows(ValidationException.class, () -> LumberTypeFacade.createLumberType(97, 97, -1, "POLE", connectionPool));
    }
}