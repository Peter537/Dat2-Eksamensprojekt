package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    void testValidGetLumbertypeById() throws DatabaseException {
        LumberType lumberType = LumberTypeFacade.getLumbertypeById(1, connectionPool).orElse(null);
        assert lumberType != null;
        assertEquals(97, lumberType.getThickness());
        assertEquals(97, lumberType.getWidth());
        assertEquals("POLE", lumberType.getType());
        assertEquals(60, lumberType.getMeterPrice());
    }

    @Test
    void testInvalidGetLumbertypeById() {
        assertThrows(DatabaseException.class, () -> LumberTypeFacade.getLumbertypeById(0, connectionPool));
    }

    @Test
    void testValidGetLumbertypeByType() throws DatabaseException {
        ArrayList<LumberType> lumberType = LumberTypeFacade.getLumbertypeByType("POLE", connectionPool).orElse(null);

        assert lumberType != null;
        for (LumberType lt : lumberType) {
            assertEquals(97, lt.getThickness());
            assertEquals(97, lt.getWidth());
            assertEquals("POLE", lt.getType());
            assertEquals(60, lt.getMeterPrice());
        }

        ArrayList<LumberType> lumberType2 = LumberTypeFacade.getLumbertypeByType("RAFTER", connectionPool).orElse(null);

        assert lumberType2 != null;

        assertEquals(45, lumberType2.get(0).getThickness());
        assertEquals(195, lumberType2.get(0).getWidth());
        assertEquals("RAFTER", lumberType2.get(0).getType());
        assertEquals(48, lumberType2.get(0).getMeterPrice());

    }

    @Test
    void testInvalidGetLumbertypeByType() {
        assertThrows(DatabaseException.class, () -> LumberTypeFacade.getLumbertypeByType("INVALID", connectionPool));
    }
}
