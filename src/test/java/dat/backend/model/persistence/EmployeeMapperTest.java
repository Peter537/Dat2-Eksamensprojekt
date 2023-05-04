package dat.backend.model.persistence;

import dat.backend.model.entities.Department;
import dat.backend.model.entities.Employee;
import dat.backend.model.entities.Position;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EmployeeMapperTest {

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
                stmt.execute("CREATE TABLE IF NOT EXISTS fogcarport_test.employee LIKE fogcarport.employee;");
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
                stmt.execute("DELETE FROM employee");
                stmt.execute("DELETE FROM department");
                stmt.execute("DELETE FROM zip");
                stmt.execute("ALTER TABLE employee AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE department AUTO_INCREMENT = 1;");

                // TODO: Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO employee (email, name, password, fk_position, fk_department_id) " +
                        "VALUES ('ben@johannesfog.dk', 'ben', '123', 'Sales', 1), ('allan@johannesfog.dk', 'allan', '1234', 'Sales', 1), ('alex@johannesfog.dk', 'alex', '12345', 'Sales', 1)");
                stmt.execute("INSERT INTO department (address, zipcode, name) VALUES ('Lyngby Adresse', 2800, 'Lyngby Trælast')");
                stmt.execute("INSERT INTO zip (zipcode, city_name) VALUES (2800, 'Lyngby')");
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
    void testValidGetEmployeeById() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertEquals(1, employee.getId());
        assertEquals("ben@johannesfog.dk", employee.getEmail());
        assertEquals("ben", employee.getName());
        assertEquals("123", employee.getPassword());
    }

    @Test
    void testInvalidGetEmployeeById() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeById(4, connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testValidGetEmployeeByEmail() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeByEmail("allan@johannesfog.dk", connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertEquals(2, employee.getId());
        assertEquals("allan@johannesfog.dk", employee.getEmail());
        assertEquals("allan", employee.getName());
        assertEquals("1234", employee.getPassword());
    }

    @Test
    void testInvalidGetEmployeeByEmail() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeByEmail("allan@johannesfog.dk", connectionPool);
        assertTrue(employeeOptional.isPresent());
    }

    @Test
    void testValidLogin() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.login("alex@johannesfog.dk", "12345", connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertEquals(3, employee.getId());
        assertEquals("alex@johannesfog.dk", employee.getEmail());
        assertEquals("alex", employee.getName());
        assertEquals("12345", employee.getPassword());
    }

    @Test
    void testInvalidLoginNotExist() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.login("myaccount@johannesfog.dk", "12345", connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testInvalidLoginWrongPassword() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.login("alex@johannesfog.dk", "1234", connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testInvalidLoginNullPassword() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.login("alex@johannesfog.dk", null, connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testValidCreateEmployee() throws DatabaseException {
        Position position = new Position("Sales");
        Department department = DepartmentMapper.getDepartmentById(1, connectionPool).orElse(null);
        Optional<Employee> employeeOptional = EmployeeFacade.createEmployee("test@johannesfog.dk", "Test", "1234566", position, department, connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertEquals(4, employee.getId());
        assertEquals("test@johannesfog.dk", employee.getEmail());
        assertEquals("Test", employee.getName());
        assertEquals("1234566", employee.getPassword());
    }

    @Test
    void testInvalidCreateEmployeeEmailAlreadyInUse() throws DatabaseException {
        Position position = new Position("Sales");
        Department department = DepartmentMapper.getDepartmentById(1, connectionPool).orElse(null);
        Optional<Employee> employeeOptional = EmployeeFacade.createEmployee("alex@johannesfog.dk", "Test", "1234566", position, department, connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testInvalidCreateEmployeeNullPassword() throws DatabaseException {
        Position position = new Position("Sales");
        Department department = DepartmentMapper.getDepartmentById(1, connectionPool).orElse(null);
        Optional<Employee> employeeOptional = EmployeeFacade.createEmployee("new@johannesfog.dk", "Test", null, position, department, connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testInvalidCreateEmployeeNullName() throws DatabaseException {
        Position position = new Position("Sales");
        Department department = DepartmentMapper.getDepartmentById(1, connectionPool).orElse(null);
        Optional<Employee> employeeOptional = EmployeeFacade.createEmployee("new@johannesfog.dk", "1234566", null, position, department, connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testInvalidCreateEmployeeEmail() throws DatabaseException {
        Position position = new Position("Sales");
        Department department = DepartmentMapper.getDepartmentById(1, connectionPool).orElse(null);
        Optional<Employee> employeeOptional = EmployeeFacade.createEmployee("test@gmail.com", "Test", "1234566", position, department, connectionPool);
        assertFalse(employeeOptional.isPresent());
    }

    @Test
    void testValidUpdatePassword() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertTrue(EmployeeFacade.updatePassword(employee, "123456", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordTooShortPassword() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertFalse(EmployeeFacade.updatePassword(employee, "12", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordTooLongPassword() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertFalse(EmployeeFacade.updatePassword(employee, "0123456789012345678901234567890123456", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordNull() throws DatabaseException {
        Optional<Employee> employeeOptional = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertTrue(employeeOptional.isPresent());
        Employee employee = employeeOptional.get();
        assertFalse(EmployeeFacade.updatePassword(employee, null, connectionPool));
    }
}