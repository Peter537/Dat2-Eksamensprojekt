package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Department;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.entities.user.Position;
import dat.backend.model.exceptions.AlreadyExistsException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest extends TestDatabase {

    @BeforeEach
    public void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM employee");
                stmt.execute("DELETE FROM department");
                stmt.execute("DELETE FROM zip");
                stmt.execute("DELETE FROM position");
                stmt.execute("ALTER TABLE employee AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE department AUTO_INCREMENT = 1;");

                // Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO employee (email, name, password, fk_position, fk_department_id) " +
                        "VALUES ('ben@johannesfog.dk', 'ben', '123', 'Sales', 1), ('allan@johannesfog.dk', 'allan', '1234', 'Sales', 1), ('alex@johannesfog.dk', 'alex', '12345', 'Sales', 1)");
                stmt.execute("INSERT INTO department (address, zipcode, name) VALUES ('Lyngby Adresse', 2800, 'Lyngby Trælast'), ('Lyngby Adresse 2', 2800, 'Lyngby Trælast 2')");
                stmt.execute("INSERT INTO zip (zipcode, city_name) VALUES (2800, 'Lyngby')");
                stmt.execute("INSERT INTO position (position) VALUES ('Sales'), ('CEO')");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testValidGetEmployeeById() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertEquals(1, employee.getId());
        assertEquals("ben@johannesfog.dk", employee.getEmail());
        assertEquals("ben", employee.getName());
        assertEquals("123", employee.getPassword());
    }

    @Test
    void testInvalidGetEmployeeById() throws DatabaseException {
        assertThrows(NotFoundException.class, () -> EmployeeFacade.getEmployeeById(4, connectionPool));
    }

    @Test
    void testValidGetEmployeeByEmail() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeByEmail("allan@johannesfog.dk", connectionPool);
        assertEquals(2, employee.getId());
        assertEquals("allan@johannesfog.dk", employee.getEmail());
        assertEquals("allan", employee.getName());
        assertEquals("1234", employee.getPassword());
    }

    @Test
    void testInvalidGetEmployeeByEmail() throws DatabaseException {
        assertThrows(NotFoundException.class, () -> EmployeeFacade.getEmployeeByEmail("kalle@johannesfog.dk", connectionPool));
    }

    @Test
    void testValidLogin() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.login("alex@johannesfog.dk", "12345", connectionPool);
        assertEquals(3, employee.getId());
        assertEquals("alex@johannesfog.dk", employee.getEmail());
        assertEquals("alex", employee.getName());
        assertEquals("12345", employee.getPassword());
    }

    @Test
    void testInvalidLoginNotExist() throws DatabaseException {
        assertThrows(NotFoundException.class, () -> EmployeeFacade.login("myaccount@johannesfog.dk", "12345", connectionPool));
    }

    @Test
    void testInvalidLoginWrongPassword() throws DatabaseException {
        assertThrows(NotFoundException.class, () -> EmployeeFacade.login("alex@johannesfog.dk", "1234", connectionPool));
    }

    @Test
    void testInvalidLoginNullPassword() throws DatabaseException {
        assertThrows(ValidationException.class, () -> EmployeeFacade.login("alex@johannesfog.dk", null, connectionPool));
    }

    @Test
    void testValidCreateEmployee() throws DatabaseException, NotFoundException, ValidationException, AlreadyExistsException {
        Position position = new Position("Sales");
        Department department = DepartmentFacade.getDepartmentById(1, connectionPool);
        Employee employee = EmployeeFacade.createEmployee("test@johannesfog.dk", "Test", "1234566", position, department, connectionPool);
        assertEquals(4, employee.getId());
        assertEquals("test@johannesfog.dk", employee.getEmail());
        assertEquals("Test", employee.getName());
        assertEquals("1234566", employee.getPassword());
    }

    @Test
    void testInvalidCreateEmployeeEmailAlreadyInUse() throws DatabaseException, NotFoundException {
        Position position = new Position("Sales");
        Department department = DepartmentFacade.getDepartmentById(1, connectionPool);
        assertThrows(AlreadyExistsException.class, () -> EmployeeFacade.createEmployee("alex@johannesfog.dk", "Test", "1234566", position, department, connectionPool));
    }

    @Test
    void testInvalidCreateEmployeeNullPassword() throws DatabaseException, NotFoundException {
        Position position = new Position("Sales");
        Department department = DepartmentFacade.getDepartmentById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.createEmployee("new@johannesfog.dk", "Test", null, position, department, connectionPool));
    }

    @Test
    void testInvalidCreateEmployeeNullName() throws DatabaseException, NotFoundException {
        Position position = new Position("Sales");
        Department department = DepartmentFacade.getDepartmentById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.createEmployee("new@johannesfog.dk", "1234566", null, position, department, connectionPool));
    }

    @Test
    void testInvalidCreateEmployeeEmail() throws DatabaseException, NotFoundException {
        Position position = new Position("Sales");
        Department department = DepartmentFacade.getDepartmentById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.createEmployee("test@gmail.com", "Test", "1234566", position, department, connectionPool));
    }

    @Test
    void testValidUpdatePassword() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertEquals("123", employee.getPassword());
        EmployeeFacade.updatePassword(employee, "123456", connectionPool);
        assertEquals("123456", employee.getPassword());
    }

    @Test
    void testInvalidUpdatePasswordTooShortPassword() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updatePassword(employee, "12", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordTooLongPassword() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updatePassword(employee, "0123456789012345678901234567890123456", connectionPool));
    }

    @Test
    void testInvalidUpdatePasswordNull() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updatePassword(employee, null, connectionPool));
    }

    @Test
    void testValidUpdateName() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertEquals("ben", employee.getName());
        EmployeeFacade.updateName(employee, "Benjamin", connectionPool);
        assertEquals("Benjamin", employee.getName());
    }

    @Test
    void testInvalidUpdateNameNull() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updateName(employee, null, connectionPool));
    }

    @Test
    void testValidUpdatePersonalPhoneNumber() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertFalse(employee.getPersonalPhoneNumber().isPresent());
        EmployeeFacade.updatePersonalPhoneNumber(employee, "87654321", connectionPool);
        assertTrue(employee.getPersonalPhoneNumber().isPresent());
        assertEquals("87654321", employee.getPersonalPhoneNumber().get());
    }

    @Test
    void testValidUpdatePersonalPhoneNumberRemoved() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertFalse(employee.getPersonalPhoneNumber().isPresent());
        EmployeeFacade.updatePersonalPhoneNumber(employee, "87654321", connectionPool);
        assertTrue(employee.getPersonalPhoneNumber().isPresent());
        assertEquals("87654321", employee.getPersonalPhoneNumber().get());
        EmployeeFacade.updatePersonalPhoneNumber(employee, null, connectionPool);
        assertFalse(employee.getPersonalPhoneNumber().isPresent());
    }

    @Test
    void testInvalidUpdatePersonalPhoneNumberTooShort() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updatePersonalPhoneNumber(employee, "1234567", connectionPool));
    }

    @Test
    void testInvalidUpdatePersonalPhoneNumberTooLong() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updatePersonalPhoneNumber(employee, "123456789012345678901", connectionPool));
    }

    @Test
    void testInvalidUpdatePersonalPhoneNumberUsingLetters() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updatePersonalPhoneNumber(employee, "1234567a", connectionPool));
    }

    @Test
    void testValidUpdateWorkPhoneNumber() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertFalse(employee.getWorkPhoneNumber().isPresent());
        EmployeeFacade.updateWorkPhoneNumber(employee, "87654321", connectionPool);
        assertTrue(employee.getWorkPhoneNumber().isPresent());
        assertEquals("87654321", employee.getWorkPhoneNumber().get());
    }

    @Test
    void testValidUpdateWorkPhoneNumberRemoved() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertFalse(employee.getWorkPhoneNumber().isPresent());
        EmployeeFacade.updateWorkPhoneNumber(employee, "87654321", connectionPool);
        assertTrue(employee.getWorkPhoneNumber().isPresent());
        assertEquals("87654321", employee.getWorkPhoneNumber().get());
        EmployeeFacade.updateWorkPhoneNumber(employee, null, connectionPool);
        assertFalse(employee.getWorkPhoneNumber().isPresent());
    }

    @Test
    void testInvalidUpdateWorkPhoneNumberTooShort() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updateWorkPhoneNumber(employee, "1234567", connectionPool));
    }

    @Test
    void testInvalidUpdateWorkPhoneNumberTooLong() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updateWorkPhoneNumber(employee, "123456789012345678901", connectionPool));
    }

    @Test
    void testInvalidUpdateWorkPhoneNumberUsingLetters() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updateWorkPhoneNumber(employee, "1234567a", connectionPool));
    }

    @Test
    void testValidUpdatePosition() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertEquals("Sales", employee.getPosition().getPositionName());
        Position position = PositionFacade.getPositionByPositionName("CEO", connectionPool);
        EmployeeFacade.updatePosition(employee, position, connectionPool);
        assertEquals("CEO", employee.getPosition().getPositionName());
    }

    @Test
    void testInvalidUpdatePositionNull() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updatePosition(employee, null, connectionPool));
    }

    @Test
    void testValidUpdateDepartment() throws DatabaseException, NotFoundException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertEquals("Lyngby Trælast", employee.getDepartment().getDepartmentName());
        Department department = DepartmentFacade.getDepartmentById(2, connectionPool);
        EmployeeFacade.updateDepartment(employee, department, connectionPool);
        assertEquals("Lyngby Trælast 2", employee.getDepartment().getDepartmentName());
    }

    @Test
    void testInvalidUpdateDepartmentNull() throws DatabaseException, NotFoundException {
        Employee employee = EmployeeFacade.getEmployeeById(1, connectionPool);
        assertThrows(ValidationException.class, () -> EmployeeFacade.updateDepartment(employee, null, connectionPool));
    }
}