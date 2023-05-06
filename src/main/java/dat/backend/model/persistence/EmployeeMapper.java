package dat.backend.model.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.*;
import dat.backend.model.services.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class EmployeeMapper {

    static Employee login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, EmployeeNotFoundException {
        String query = "SELECT * FROM employee WHERE email = ? AND password = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                return createEmployeeFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not login employee");
        }
    }

    static Employee createEmployee(String email, String name, String password, Position position, Department department, ConnectionPool connectionPool) throws DatabaseException, ValidationException, EmployeeAlreadyExistsException {
        Validation.validateEmployee(name, email, password);
        try {
            getEmployeeByEmail(email, connectionPool);
            throw new EmployeeAlreadyExistsException("Email already exists");
        } catch (EmployeeNotFoundException e) {
            // Do nothing
        }

        String query = "INSERT INTO employee (email, name, password, fk_position, fk_department_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, name);
                statement.setString(3, password);
                statement.setString(4, position.getPositionName());
                statement.setInt(5, department.getId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Could not create employee");
                }

                return getEmployeeByEmail(email, connectionPool);
            }
        } catch (SQLException | EmployeeNotFoundException e) {
            throw new DatabaseException(e, "Could not create employee");
        }
    }

    static Employee getEmployeeById(int id, ConnectionPool connectionPool) throws DatabaseException, EmployeeNotFoundException {
        String query = "SELECT * FROM employee WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                return createEmployeeFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get employee");
        }
    }

    static Employee getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, EmployeeNotFoundException {
        String query = "SELECT * FROM employee WHERE email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                return createEmployeeFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get employee");
        }
    }

    static void updatePassword(Employee employee, String newPassword, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateEmployee(employee.getName(), employee.getEmail(), newPassword);
        String query = "UPDATE employee SET password = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPassword);
                statement.setInt(2, employee.getId());
                statement.executeUpdate();
                employee.setPassword(newPassword);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update password");
        }
    }

    private static Employee createEmployeeFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws SQLException, DatabaseException, EmployeeNotFoundException {
        if (!resultSet.next()) {
            throw new EmployeeNotFoundException("Could not find employee");
        }

        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String positionName = resultSet.getString("fk_position");
        Optional<String> workPhoneNumber = Optional.ofNullable(resultSet.getString("work_phonenumber"));
        Optional<String> privatePhoneNumber = Optional.ofNullable(resultSet.getString("private_phonenumber"));
        Position position = new Position(positionName);
        int departmentId = resultSet.getInt("fk_department_id");
        try {
            Department department = DepartmentFacade.getDepartmentById(departmentId, connectionPool);
            return new Employee(id, email, name, password, workPhoneNumber, privatePhoneNumber, position, department);
        } catch (DepartmentNotFoundException e) {
            throw new DatabaseException(e, "Could not get department");
        }
    }
}