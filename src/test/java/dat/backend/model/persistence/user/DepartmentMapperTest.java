package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Department;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.DepartmentNotFoundException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DepartmentMapperTest {

    private final static String USER = "root";
    private final static String PASSWORD = "123";
    private final static String URL = "jdbc:mysql://localhost:3306/fogcarport_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE IF NOT EXISTS fogcarport_test;");

                // TODO: Create user table. Add your own tables here
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.department LIKE fogcarport.department;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.zip LIKE fogcarport.zip;");
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
                // TODO: Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM department");
                stmt.execute("DELETE FROM zip");
                stmt.execute("ALTER TABLE department AUTO_INCREMENT = 1;");

                // TODO: Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO department (address, zipcode, name) " +
                        "VALUES ('Lyngby Adresse', 2800, 'Lyngby Trælast'), ('København Adresse', 1400, 'København Trælast'), ('Hillerød Adresse', 3400, 'Hillerød Trælast')");
                stmt.execute("INSERT INTO zip (zipcode, city_name) VALUES (2800, 'Lyngby'), (1400, 'København'), (3400, 'Hillerød')");
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
    void testValidGetDepartmentById() throws DatabaseException, NotFoundException {
        Department department = DepartmentFacade.getDepartmentById(1, connectionPool);
        assertNotNull(department);
        assertEquals(1, department.getId());
        assertEquals("Lyngby Adresse", department.getAddress().getStreet());
        assertEquals(2800, department.getAddress().getZip().getZipCode());
        assertEquals("Lyngby Trælast", department.getDepartmentName());
    }

    @Test
    void testInvalidGetDepartmentById() {
        assertThrows(NotFoundException.class, () -> DepartmentFacade.getDepartmentById(0, connectionPool));
    }
}