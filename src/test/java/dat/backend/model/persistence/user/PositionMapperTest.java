package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Position;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PositionMapperTest extends TestDatabase {
    @BeforeEach
    public void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM position");

                // Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO position (position) VALUES ('Sales'), ('CEO'), ('Lagermedarbejder')");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testValidGetPositionByPositionName() throws NotFoundException, DatabaseException {
        Position position = PositionFacade.getPositionByPositionName("Sales", connectionPool);
        assertNotNull(position);
        assertEquals("Sales", position.getPositionName());
    }

    @Test
    void testInvalidGetPositionByPositionName() {
        assertThrows(NotFoundException.class, () -> PositionFacade.getPositionByPositionName("Salesman", connectionPool));
    }
}