package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.*;
import dat.backend.model.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
                stmt.execute("DELETE FROM customer");
                stmt.execute("DELETE FROM zip");
                stmt.execute("ALTER TABLE customer AUTO_INCREMENT = 1;");

                // TODO: Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO customer (email, name, password) " +
                        "VALUES ('ben@gmail.com', 'ben', '123'), ('allan@outlook.dk', 'allan', '1234'), ('alex@hotmail.com', 'alex', '12345')");
                stmt.execute("INSERT INTO zip (zipcode, city_name)" +
                        " VALUES ('2800', 'Lyngby'), ('2730', 'Herlev')");
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
    void testValidGetCustomerById() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertEquals(1, customer.getId());
        assertEquals("ben@gmail.com", customer.getEmail());
        assertEquals("ben", customer.getName());
        assertEquals("123", customer.getPassword());
    }

    @Test
    void testInvalidGetCustomerById() throws DatabaseException {
        assertThrows(CustomerNotFoundException.class, () -> CustomerFacade.getCustomerById(4, connectionPool));
    }

    @Test
    void testValidGetCustomerByEmail() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerByEmail("allan@outlook.dk", connectionPool);
        assertEquals(2, customer.getId());
        assertEquals("allan@outlook.dk", customer.getEmail());
        assertEquals("allan", customer.getName());
        assertEquals("1234", customer.getPassword());
    }

    @Test
    void testInvalidGetCustomerByEmail() throws DatabaseException {
        assertThrows(CustomerNotFoundException.class, () -> CustomerFacade.getCustomerByEmail("kalle@outlook.dk", connectionPool));
    }

    @Test
    void testValidLogin() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.login("alex@hotmail.com", "12345", connectionPool);
        assertEquals(3, customer.getId());
        assertEquals("alex@hotmail.com", customer.getEmail());
        assertEquals("alex", customer.getName());
        assertEquals("12345", customer.getPassword());
    }

    @Test
    void testInvalidLoginNotExist() throws DatabaseException {
        assertThrows(CustomerNotFoundException.class, () -> CustomerFacade.login("myaccount@gmail.com", "12345", connectionPool));
    }

    @Test
    void testInvalidLoginWrongPassword() throws DatabaseException {
        assertThrows(CustomerNotFoundException.class, () -> CustomerFacade.login("alex@hotmail.com", "1234", connectionPool));
    }

    @Test
    void testInvalidLoginNullPassword() throws DatabaseException {
        assertThrows(CustomerNotFoundException.class, () -> CustomerFacade.login("alex@hotmail.com", null, connectionPool));
    }

    @Test
    void testInvalidLoginNullEmail() throws DatabaseException {
        assertThrows(CustomerNotFoundException.class, () -> CustomerFacade.login(null, "12345", connectionPool));
    }

    @Test
    void testValidCreateCustomer() throws DatabaseException, ValidationException, CustomerAlreadyExistsException {
        Customer customer = CustomerFacade.createCustomer("test@gmail.com", "1234566", "Test", connectionPool);
        assertEquals(4, customer.getId());
        assertEquals("test@gmail.com", customer.getEmail());
        assertEquals("Test", customer.getName());
        assertEquals("1234566", customer.getPassword());
    }

    @Test
    void testInvalidCreateCustomerEmailAlreadyInUse() throws DatabaseException {
        assertThrows(CustomerAlreadyExistsException.class, () -> CustomerFacade.createCustomer("alex@hotmail.com", "1234566", "Test", connectionPool));
    }

    @Test
    void testInvalidCreateCustomerNullPassword() throws DatabaseException {
        assertThrows(ValidationException.class, () -> CustomerFacade.createCustomer("new@gmail.com", null, "Test", connectionPool));
    }

    @Test
    void testInvalidCreateCustomerNullName() throws DatabaseException {
        assertThrows(ValidationException.class, () -> CustomerFacade.createCustomer("new@gmail.com", "1234566", null, connectionPool));
    }

    @Test
    void testValidUpdatePassword() throws DatabaseException, CustomerNotFoundException, ValidationException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertEquals("123", customer.getPassword());
        CustomerFacade.updatePassword(customer, "123456", connectionPool);
        assertEquals("123456", customer.getPassword());
    }

    @Test
    void testInvalidUpdatePasswordTooShortPassword() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CustomerFacade.updatePassword(customer, "12", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordTooLongPassword() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CustomerFacade.updatePassword(customer, "0123456789012345678901234567890123456", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordNull() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CustomerFacade.updatePassword(customer, null, connectionPool));
    }

    @Test
    void testValidUpdateName() throws DatabaseException, CustomerNotFoundException, ValidationException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertEquals("ben", customer.getName());
        CustomerFacade.updateName(customer, "Benjamin", connectionPool);
        assertEquals("Benjamin", customer.getName());
    }

    @Test
    void testInvalidUpdateNameNull() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CustomerFacade.updateName(customer, null, connectionPool));
    }

    @Test
    void testValidUpdatePhoneNumber() throws DatabaseException, CustomerNotFoundException, ValidationException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertFalse(customer.getPersonalPhoneNumber().isPresent());
        CustomerFacade.updatePhoneNumber(customer, "12345678", connectionPool);
        assertTrue(customer.getPersonalPhoneNumber().isPresent());
        assertEquals("12345678", customer.getPersonalPhoneNumber().get());
    }

    @Test
    void testValidUpdatePhoneNumberRemoved() throws CustomerNotFoundException, DatabaseException, ValidationException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertFalse(customer.getPersonalPhoneNumber().isPresent());
        CustomerFacade.updatePhoneNumber(customer, "12345678", connectionPool);
        assertTrue(customer.getPersonalPhoneNumber().isPresent());
        assertEquals("12345678", customer.getPersonalPhoneNumber().get());
        CustomerFacade.updatePhoneNumber(customer, null, connectionPool);
        assertFalse(customer.getPersonalPhoneNumber().isPresent());
    }

    @Test
    void testInvalidUpdatePhoneNumberTooShort() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CustomerFacade.updatePhoneNumber(customer, "1234567", connectionPool));
    }

    @Test
    void testInvalidUpdatePhoneNumberTooLong() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CustomerFacade.updatePhoneNumber(customer, "123456789", connectionPool));
    }

    @Test
    void testInvalidUpdatePhoneNumberUsingLetters() throws DatabaseException, CustomerNotFoundException {
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CustomerFacade.updatePhoneNumber(customer, "1234567a", connectionPool));
    }

    @Test
    void testValidUpdateAddress_1() throws DatabaseException, CustomerNotFoundException, ValidationException, ZipNotFoundException {
        Zip zip = ZipFacade.getZipByZipCode(2800, connectionPool);
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertFalse(customer.getAddress(1).isPresent());
        CustomerFacade.updateAddress(customer, 1, "Testvej 1", zip, connectionPool);
        assertTrue(customer.getAddress(1).isPresent());
        assertEquals("Testvej 1", customer.getAddress(1).get().getStreet());
    }

    @Test
    void testValidUpdateAddress_2() throws DatabaseException, CustomerNotFoundException, ValidationException, ZipNotFoundException {
        Zip zip = ZipFacade.getZipByZipCode(2800, connectionPool);
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertFalse(customer.getAddress(2).isPresent());
        CustomerFacade.updateAddress(customer, 2, "Testvej 2", zip, connectionPool);
        assertTrue(customer.getAddress(2).isPresent());
        assertEquals("Testvej 2", customer.getAddress(2).get().getStreet());
    }

    @Test
    void testValidUpdateAddress_3() throws DatabaseException, CustomerNotFoundException, ValidationException, ZipNotFoundException {
        Zip zip = ZipFacade.getZipByZipCode(2730, connectionPool);
        Customer customer = CustomerFacade.getCustomerById(1, connectionPool);
        assertFalse(customer.getAddress(3).isPresent());
        CustomerFacade.updateAddress(customer, 3, "Testvej 3", zip, connectionPool);
        assertTrue(customer.getAddress(3).isPresent());
        assertEquals("Testvej 3", customer.getAddress(3).get().getStreet());
    }
}