package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ZipMapperTest extends TestDatabase {

    @BeforeEach
    public void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM zip");

                // Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO zip (zipcode, city_name) VALUES (2800, 'Lyngby'), (1400, 'København'), (3400, 'Hillerød')");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testValidGetZipByZipCode() throws NotFoundException, DatabaseException {
        Zip zip = ZipFacade.getZipByZipCode(2800, connectionPool);
        assertNotNull(zip);
        assertEquals(2800, zip.getZipCode());
        assertEquals("Lyngby", zip.getCityName());
    }

    @Test
    void testInvalidGetZipByZipCode() {
        assertThrows(NotFoundException.class, () -> ZipFacade.getZipByZipCode(9999, connectionPool));
    }
}