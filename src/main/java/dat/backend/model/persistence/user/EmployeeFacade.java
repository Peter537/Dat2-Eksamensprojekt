package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Department;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.entities.user.Position;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.EmployeeAlreadyExistsException;
import dat.backend.model.exceptions.EmployeeNotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;

public class EmployeeFacade {

    public static Employee login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, EmployeeNotFoundException {
        return EmployeeMapper.login(email, password, connectionPool);
    }

    public static Employee createEmployee(String email, String name, String password, Position position, Department department, ConnectionPool connectionPool) throws DatabaseException, ValidationException, EmployeeAlreadyExistsException {
        return EmployeeMapper.createEmployee(email, name, password, position, department, connectionPool);
    }

    public static Employee getEmployeeById(int id, ConnectionPool connectionPool) throws DatabaseException, EmployeeNotFoundException {
        return EmployeeMapper.getEmployeeById(id, connectionPool);
    }

    public static Employee getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, EmployeeNotFoundException {
        return EmployeeMapper.getEmployeeByEmail(email, connectionPool);
    }

    public static void updatePassword(Employee employee, String newPassword, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        EmployeeMapper.updatePassword(employee, newPassword, connectionPool);
    }

    public static void updateName(Employee employee, String newName, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        EmployeeMapper.updateName(employee, newName, connectionPool);
    }

    public static void updatePersonalPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        EmployeeMapper.updatePersonalPhoneNumber(employee, newPhoneNumber, connectionPool);
    }

    public static void updateWorkPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        EmployeeMapper.updateWorkPhoneNumber(employee, newPhoneNumber, connectionPool);
    }

    public static void updatePosition(Employee employee, Position newPosition, ConnectionPool connectionPool) throws DatabaseException {
        EmployeeMapper.updatePosition(employee, newPosition, connectionPool);
    }

    public static void updateDepartment(Employee employee, Department newDepartment, ConnectionPool connectionPool) throws DatabaseException {
        EmployeeMapper.updateDepartment(employee, newDepartment, connectionPool);
    }
}