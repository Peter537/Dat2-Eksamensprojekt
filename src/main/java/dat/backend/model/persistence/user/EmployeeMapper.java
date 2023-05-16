package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.*;
import dat.backend.model.exceptions.*;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class EmployeeMapper {

    static Employee login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        Validation.validateEmployee(email, password);
        String query = "SELECT * FROM employeeWithDepartment WHERE email = ? AND password = ?";
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

    static Employee createEmployee(String email, String name, String password, Position position, Department department, ConnectionPool connectionPool) throws DatabaseException, ValidationException, AlreadyExistsException {
        Validation.validateEmployee(name, email, password);
        try {
            getEmployeeByEmail(email, connectionPool);
            throw new AlreadyExistsException("Email already exists");
        } catch (NotFoundException e) {
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
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Could not create employee");
        }
    }

    static Employee getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM employeeWithDepartment WHERE email = ?";
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

    static void updateName(Employee employee, String newName, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateEmployee(newName, employee.getEmail(), employee.getPassword());
        String query = "UPDATE employee SET name = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newName);
                statement.setInt(2, employee.getId());
                statement.executeUpdate();
                employee.setName(newName);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update name");
        }
    }

    static void updatePersonalPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePhoneNumber(newPhoneNumber);
        String query = "UPDATE employee SET private_phonenumber = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPhoneNumber);
                statement.setInt(2, employee.getId());
                statement.executeUpdate();
                employee.setPersonalPhoneNumber(Optional.ofNullable(newPhoneNumber));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update personal phone number");
        }
    }

    static void updateWorkPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePhoneNumber(newPhoneNumber);
        String query = "UPDATE employee SET work_phonenumber = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPhoneNumber);
                statement.setInt(2, employee.getId());
                statement.executeUpdate();
                employee.setWorkPhoneNumber(Optional.ofNullable(newPhoneNumber));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update work phone number");
        }
    }

    static void updatePosition(Employee employee, Position newPosition, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePosition(newPosition);
        String query = "UPDATE employee SET fk_position = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPosition.getPositionName());
                statement.setInt(2, employee.getId());
                statement.executeUpdate();
                employee.setPosition(newPosition);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update position");
        }
    }

    static void updateDepartment(Employee employee, Department newDepartment, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateDepartment(newDepartment);
        String query = "UPDATE employee SET fk_department_id = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, newDepartment.getId());
                statement.setInt(2, employee.getId());
                statement.executeUpdate();
                employee.setDepartment(newDepartment);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update department");
        }
    }

    private static Employee createEmployeeFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws SQLException, DatabaseException, NotFoundException {
        if (!resultSet.next()) {
            throw new NotFoundException("Could not find employee");
        }

        int id = resultSet.getInt("employeeid");
        String email = resultSet.getString("email");
        String name = resultSet.getString("employeename");
        String password = resultSet.getString("password");
        String positionName = resultSet.getString("fk_position");
        Optional<String> privatePhoneNumber = Optional.ofNullable(resultSet.getString("private_phonenumber"));
        Optional<String> workPhoneNumber = Optional.ofNullable(resultSet.getString("work_phonenumber"));

        int departmentId = resultSet.getInt("id");
        String departmentStreet = resultSet.getString("address");
        Zip departmentZipCode = new Zip(resultSet.getInt("zipcode"), resultSet.getString("city_name"));
        Address departmentAddress = new Address(departmentStreet, departmentZipCode);
        String departmentName = resultSet.getString("name");

        Position position = new Position(positionName);
        Department department = new Department(departmentId, departmentName, departmentAddress);
        return new Employee(id, email, name, password, privatePhoneNumber, workPhoneNumber, position, department);
    }
}