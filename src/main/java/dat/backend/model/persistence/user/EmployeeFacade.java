package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Department;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.entities.user.Position;
import dat.backend.model.exceptions.AlreadyExistsException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.Validation;

public class EmployeeFacade {

    public static Employee login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        Validation.validateEmployee(email, password);
        return EmployeeMapper.login(email, password, connectionPool);
    }

    public static Employee create(String email, String name, String password, Position position, Department department, ConnectionPool connectionPool) throws DatabaseException, ValidationException, AlreadyExistsException {
        Validation.validateEmployee(name, email, password);
        try {
            getEmployeeByEmail(email, connectionPool);
            throw new AlreadyExistsException("Email already exists");
        } catch (NotFoundException e) {
            // Do nothing
        }

        return EmployeeMapper.create(email, name, password, position, department, connectionPool);
    }

    public static Employee getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return EmployeeMapper.getEmployeeByEmail(email, connectionPool);
    }

    public static void updatePassword(Employee employee, String newPassword, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateEmployee(employee.getName(), employee.getEmail(), newPassword);
        EmployeeMapper.updatePassword(employee, newPassword, connectionPool);
    }

    public static void updateName(Employee employee, String newName, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateName(newName);
        EmployeeMapper.updateName(employee, newName, connectionPool);
    }

    public static void updatePersonalPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePhoneNumber(newPhoneNumber);
        EmployeeMapper.updatePersonalPhoneNumber(employee, newPhoneNumber, connectionPool);
    }

    public static void updateWorkPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePhoneNumber(newPhoneNumber);
        EmployeeMapper.updateWorkPhoneNumber(employee, newPhoneNumber, connectionPool);
    }

    public static void updatePosition(Employee employee, Position newPosition, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePosition(newPosition);
        EmployeeMapper.updatePosition(employee, newPosition, connectionPool);
    }

    public static void updateDepartment(Employee employee, Department newDepartment, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateDepartment(newDepartment);
        EmployeeMapper.updateDepartment(employee, newDepartment, connectionPool);
    }
}