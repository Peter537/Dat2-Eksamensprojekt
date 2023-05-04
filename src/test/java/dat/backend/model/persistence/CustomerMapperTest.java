package dat.backend.model.persistence;

import dat.backend.model.entities.Customer;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

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
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.customer LIKE fogcarport.customer;");
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
                stmt.execute("DELETE FROM customer");
                stmt.execute("ALTER TABLE customer AUTO_INCREMENT = 1;");

                // TODO: Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO customer (email, name, password) " +
                        "VALUES ('ben@gmail.com', 'ben', '123'), ('allan@outlook.dk', 'allan', '1234'), ('alex@hotmail.com', 'alex', '12345')");
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
    void testValidGetCustomerById() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerById(1, connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertEquals(1, customer.getId());
        assertEquals("ben@gmail.com", customer.getEmail());
        assertEquals("ben", customer.getName());
        assertEquals("123", customer.getPassword());
    }

    @Test
    void testInvalidGetCustomerById() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerById(4, connectionPool);
        assertFalse(customerOptional.isPresent());
    }

    @Test
    void testValidGetCustomerByEmail() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerByEmail("allan@outlook.dk", connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertEquals(2, customer.getId());
        assertEquals("allan@outlook.dk", customer.getEmail());
        assertEquals("allan", customer.getName());
        assertEquals("1234", customer.getPassword());
    }

    @Test
    void testInvalidGetCustomerByEmail() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerByEmail("allan@outlook.dk", connectionPool);
        assertTrue(customerOptional.isPresent());
    }

    @Test
    void testValidLogin() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.login("alex@hotmail.com", "12345", connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertEquals(3, customer.getId());
        assertEquals("alex@hotmail.com", customer.getEmail());
        assertEquals("alex", customer.getName());
        assertEquals("12345", customer.getPassword());
    }

    @Test
    void testInvalidLoginNotExist() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.login("myaccount@gmail.com", "12345", connectionPool);
        assertFalse(customerOptional.isPresent());
    }

    @Test
    void testInvalidLoginWrongPassword() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.login("alex@hotmail.com", "1234", connectionPool);
        assertFalse(customerOptional.isPresent());
    }

    @Test
    void testInvalidLoginNullPassword() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.login("alex@hotmail.com", null, connectionPool);
        assertFalse(customerOptional.isPresent());
    }

    @Test
    void testValidCreateCustomer() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.createCustomer("test@gmail.com", "1234566", "Test", connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertEquals(4, customer.getId());
        assertEquals("test@gmail.com", customer.getEmail());
        assertEquals("Test", customer.getName());
        assertEquals("1234566", customer.getPassword());
    }

    @Test
    void testInvalidCreateCustomerEmailAlreadyInUse() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.createCustomer("alex@hotmail.com", "1234566", "Test", connectionPool);
        assertFalse(customerOptional.isPresent());
        //assertThrows(DatabaseException.class, () -> CustomerFacade.createCustomer("alex@hotmail.com", "1234566", "Test", connectionPool));
    }

    @Test
    void testInvalidCreateCustomerNullPassword() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.createCustomer("new@gmail.com", null, "Test", connectionPool);
        assertFalse(customerOptional.isPresent());
    }

    @Test
    void testInvalidCreateCustomerNullName() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.createCustomer("new@gmail.com,", "1234566", null, connectionPool);
        assertFalse(customerOptional.isPresent());
    }

    @Test
    void testValidUpdatePassword() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerById(1, connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertTrue(CustomerFacade.updatePassword(customer, "123456", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordTooShortPassword() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerById(1, connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertFalse(CustomerFacade.updatePassword(customer, "12", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordTooLongPassword() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerById(1, connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertFalse(CustomerFacade.updatePassword(customer, "0123456789012345678901234567890123456", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordNull() throws DatabaseException {
        Optional<Customer> customerOptional = CustomerFacade.getCustomerById(1, connectionPool);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertFalse(CustomerFacade.updatePassword(customer, null, connectionPool));
    }
}