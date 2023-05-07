package dat.backend.model.persistence;

import dat.backend.model.entities.Department;
import dat.backend.model.entities.Employee;
import dat.backend.model.entities.Position;
import dat.backend.model.exceptions.DatabaseException;

import java.util.Optional;

public class EmployeeFacade {

    public static Optional<Employee> login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        return EmployeeMapper.login(email, password, connectionPool);
    }

    public static Optional<Employee> createEmployee(String email, String name, String password, Position position, Department department, ConnectionPool connectionPool) throws DatabaseException {
        return EmployeeMapper.createEmployee(email, name, password, position, department, connectionPool);
    }

    public static Optional<Employee> getEmployeeById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return EmployeeMapper.getEmployeeById(id, connectionPool);
    }

    public static Optional<Employee> getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException {
        return EmployeeMapper.getEmployeeByEmail(email, connectionPool);
    }

    public static void updatePassword(Employee employee, String newPassword, ConnectionPool connectionPool) throws DatabaseException {
        EmployeeMapper.updatePassword(employee, newPassword, connectionPool);
    }
}