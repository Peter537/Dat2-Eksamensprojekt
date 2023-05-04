package dat.backend.model.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class EmployeeMapper {

    static Optional<Employee> login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
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

    static Optional<Employee> createEmployee(String email, String name, String password, Position position, Department department, ConnectionPool connectionPool) throws DatabaseException {
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
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException(e, "Could not create employee");
        }
    }

    static Optional<Employee> getEmployeeById(int id, ConnectionPool connectionPool) throws DatabaseException {
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

    static Optional<Employee> getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException {
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

    static void updatePassword(Employee employee, String newPassword, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE employee SET password = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPassword);
                statement.setInt(2, employee.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update password");
        }
    }

    private static Optional<Employee> createEmployeeFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        if (!resultSet.next()) {
            return Optional.empty();
        }

        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String positionName = resultSet.getString("fk_position");
        Position position = new Position(positionName);
        int departmentId = resultSet.getInt("fk_department_id");
        Optional<Department> department = DepartmentMapper.getDepartmentById(departmentId, connectionPool);
        if (!department.isPresent()) {
            throw new DatabaseException("Could not get department");
        }

        return Optional.of(new Employee(id, email, name, password, null, null, position, department.get()));
    }
}