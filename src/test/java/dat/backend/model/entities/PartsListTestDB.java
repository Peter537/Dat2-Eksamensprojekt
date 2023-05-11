package dat.backend.model.entities;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.TestDatabase;
import dat.backend.model.persistence.item.LumbertypeFacade;
import dat.backend.model.persistence.item.LumbertypeMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PartsListTestDB extends TestDatabase {

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
                        " VALUES (300, 1, 1000), (480, 1, 1000), (360, 2, 1000), (360, 2, 1000), (720,2,1000), (720, 3, 1000)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }




    @Test
    void calculateRafterType() throws DatabaseException {
        // arrange

        double expectedWidth = 195;
        String expectedType = "RAFTER";
        int width = 672;

        //act
        LumberType rafterType = PartsList.calculateRafterType(width, connectionPool);
        // assert
        assertEquals(expectedWidth, rafterType.getWidth());
        assertEquals(expectedType, rafterType.getType());


    }
    @Test
    void calculatePole() throws DatabaseException {
        // arrange
        int expectedLength = 480;
        int width = 672;
        int height = 200;

        //act
        Lumber pole = PartsList.calculatePole(height, width, connectionPool);

        // assert
        assertEquals(expectedLength, pole.getLength());
    }
    @Test
    void calculateRafter() throws DatabaseException {
        // arrange
        int expectedLength = 480;
        int width = 672;
        int length = 480;

        //act
        Lumber rafter = PartsList.calculateRafter(length, width, connectionPool);

    }

    @Test
    void calculatePlate() {
    }

    @Test
    void calculateTotalPrice() {
    }
}