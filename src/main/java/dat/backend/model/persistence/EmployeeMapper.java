package dat.backend.model.persistence;

import dat.backend.model.entities.Department;
import dat.backend.model.entities.Employee;

import java.util.Optional;

class EmployeeMapper {

    static Optional<Employee> login(String email, String password, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static Optional<Employee> createEmployee(String email, String password, String name, Department department, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static Optional<Employee> getEmployeeById(int id, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static Optional<Employee> getEmployeeByEmail(String email, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static void updatePassword(Employee employee, String newPassword, ConnectionPool connectionPool) {
    }
}