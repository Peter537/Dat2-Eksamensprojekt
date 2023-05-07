package dat.backend.model.persistence;

import dat.backend.model.entities.Department;
import dat.backend.model.entities.Employee;
import dat.backend.model.entities.Position;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.EmployeeAlreadyExistsException;
import dat.backend.model.exceptions.EmployeeNotFoundException;
import dat.backend.model.exceptions.ValidationException;

import java.util.Optional;

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
}