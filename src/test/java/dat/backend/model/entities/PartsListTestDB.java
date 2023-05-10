package dat.backend.model.entities;

import dat.backend.model.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class PartsListTestDB {


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




    @Test
    void calculatePole() {
        // arrange
        int expectedLength = 480;
        //act

        // assert
    }

    @Test
    void calculateRafterType() {
    }

    @Test
    void calculateRafter() {
    }

    @Test
    void calculatePlate() {
    }
}