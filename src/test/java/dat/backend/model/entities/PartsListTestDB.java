package dat.backend.model.entities;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.TestDatabase;
import dat.backend.model.persistence.item.RoofFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PartsListTestDB extends TestDatabase {

    @BeforeEach
    public void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM roof");
                stmt.execute("DELETE FROM lumber");
                stmt.execute("DELETE FROM lumbertype");
                stmt.execute("DELETE FROM type");
                stmt.execute("ALTER TABLE lumber AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE lumbertype AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE roof AUTO_INCREMENT = 1;");


                // Insert a few lumbers - insert rows into your own tables here
                stmt.execute("INSERT INTO type (type, displayname) " +
                        "VALUES ('RAFTER', 'Spærtræ'), ('POLE', 'Stolpe'), ('PLASTIC_ROOF', 'Plastic tag')");
                stmt.execute("INSERT INTO lumbertype (thickness, width, type, meter_price) " +
                        "VALUES (97, 97, 'POLE', 60), (45, 195, 'RAFTER', 48), (45, 245, 'RAFTER', 82)");
                stmt.execute("INSERT INTO lumber (length, type, amount)" +
                        " VALUES (300, 1, 1000), (480, 1, 1000), (360, 2, 1000), (360, 2, 1000), (720,2,1000), (800,2,1000), (720, 3, 1000)");
                stmt.execute("INSERT INTO roof (squaremeter_price, type) " +
                        "VALUES (100, 'PLASTIC_ROOF'), (200, 'TILED_ROOF')");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }


    @Test
    void testValidCalculateRafterType() throws DatabaseException {
        // arrange

        double expectedWidth = 195;
        String expectedType = "RAFTER";
        int width = 672;

        //act
        LumberType rafterType = PartsList.calculateRafterType(width, super.connectionPool);
        // assert
        assertEquals(expectedWidth, rafterType.getWidth());
        assertEquals(expectedType, rafterType.getType());


    }

    @Test
    void testValidCalculatePole() throws DatabaseException {
        // arrange
        int expectedLength = 480;
        int width = 672;
        int height = 200;

        //act
        Lumber pole = PartsList.calculatePole(height, width, super.connectionPool);

        // assert
        assertEquals(expectedLength, pole.getLength());
    }

    @Test
    void testValidCalculateRafter() throws DatabaseException {
        // arrange
        int expectedLength = 720; // Rafter goes lengthwise in the carport
        int width = 672;
        int length = 400;

        //act
        Lumber rafter = PartsList.calculateRafter(length, width, super.connectionPool);

        // assert
        assertEquals(expectedLength, rafter.getLength());

    }

    @Test
    void testValidCalculatePlate() throws DatabaseException {

        // arrange
        int expectedLength = 720; // Plate goes Widthwise in the carport
        int width = 672;
        int length = 400;

        //act
        Lumber rafter = PartsList.calculatePlate(width, super.connectionPool);

        // assert
        assertEquals(expectedLength, rafter.getLength());

    }

    @Test
    void testValidCalculateTotalPrice() throws DatabaseException, NotFoundException {
        // arrange
        int height = 200;
        int width = 672;
        int length = 400;
        PartsList partsList = new PartsList(height, length, width, super.connectionPool);
        Roof roof = RoofFacade.getRoofById(1, super.connectionPool);
        float pricePoles = PartsList.calculateNumberOfPoles(length, width) * PartsList.calculatePole(height, width, super.connectionPool).getPrice();
        double expectedPrice = PartsList.calculateNumberOfPoles(length, width) * PartsList.calculatePole(height, width, super.connectionPool).getPrice() +
                PartsList.calculateNumberOfRafters(length) * PartsList.calculateRafter(length, width, super.connectionPool).getPrice() +
                PartsList.calculateNumberOfPlates(width) * PartsList.calculatePlate(width, super.connectionPool).getPrice() + partsList.getRoof().getSquareMeterPrice() * partsList.getRoofArea();
        //act
        double totalPrice = partsList.calculateTotalPrice();
        // assert
        assertEquals(expectedPrice, totalPrice, 0.1);
    }

    @Test
    void calculateNumber() throws DatabaseException {
        // arrange
        int expected = 2;
        int width = 672;
        LumberType rafterType = PartsList.calculateRafterType(width, super.connectionPool);

        //act
        int actual = PartsList.calculateNumber(1500, rafterType, super.connectionPool);

        // assert
        assertEquals(expected, actual);

    }

    @Test
    void testValidCalculateLengthOfLumber0() throws NotFoundException, DatabaseException {

        int width = 672;
        LumberType rafterType = PartsList.calculateRafterType(width, super.connectionPool);
        int expected = 672;

        int actual = PartsList.calculateLengthOfLumber(width, rafterType, super.connectionPool);
        assertEquals(expected, actual);
    }
/*
    @Test
    void testValidCalculateLengthOfLumber1() {
        int expected = 400;
        int actual = PartsList.calculateLengthOfLumber(800);
        assertEquals(expected, actual);
    }
    */
}