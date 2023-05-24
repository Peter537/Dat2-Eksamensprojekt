package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class EmployeeMapper {

    /**
     * This method will login an employee by email and password
     *
     * @param email          The email to search for
     * @param password       The password to search for
     * @param connectionPool Connection pool
     * @return The Employee object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the employee does not exist
     */
    static Employee login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM employeeWithDepartment WHERE email = ? AND password = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                return createEmployeeFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not login employee");
        }
    }

    /**
     * This method will create an Employee by the arguments
     *
     * @param email          The email of the employee
     * @param name           The name of the employee
     * @param password       The password of the employee
     * @param position       The position of the employee
     * @param department     The department of the employee
     * @param connectionPool Connection pool
     * @return The employee object
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static Employee create(String email, String name, String password, Position position, Department department, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will retrieve an employee by email
     *
     * @param email          The email to search for
     * @param connectionPool Connection pool
     * @return The employee object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the employee does not exist
     */
    static Employee getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM employeeWithDepartment WHERE email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                return createEmployeeFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get employee");
        }
    }

    /**
     * This method will update the password of an employee
     *
     * @param employee       The employee to update
     * @param newPassword    The new password
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
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

    /**
     * This method will update the name of an employee
     *
     * @param employee       The employee to update
     * @param newName        The new name
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateName(Employee employee, String newName, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the personal phone number of an employee
     *
     * @param employee       The employee to update
     * @param newPhoneNumber The new phone number
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updatePersonalPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the work phone number of an employee
     *
     * @param employee       The employee to update
     * @param newPhoneNumber The new phone number
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateWorkPhoneNumber(Employee employee, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the position of an employee
     *
     * @param employee       The employee to update
     * @param newPosition    The new position
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updatePosition(Employee employee, Position newPosition, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the department of an employee
     *
     * @param employee       The employee to update
     * @param newDepartment  The new department
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateDepartment(Employee employee, Department newDepartment, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will create an Employee object from a ResultSet
     *
     * @param resultSet The ResultSet to create an Employee object from
     * @return An Employee object
     * @throws SQLException      If an error occurs while communicating with the database
     * @throws NotFoundException If the ResultSet is empty
     */
    private static Employee createEmployeeFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {
        if (!resultSet.next()) {
            throw new NotFoundException("Could not find employee");
        }

        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String positionName = resultSet.getString("fk_position");
        Optional<String> privatePhoneNumber = Optional.ofNullable(resultSet.getString("private_phonenumber"));
        Optional<String> workPhoneNumber = Optional.ofNullable(resultSet.getString("work_phonenumber"));

        int departmentId = resultSet.getInt("departmentid");
        String departmentStreet = resultSet.getString("address");
        Zip departmentZipCode = new Zip(resultSet.getInt("zipcode"), resultSet.getString("city_name"));
        Address departmentAddress = new Address(departmentStreet, departmentZipCode);
        String departmentName = resultSet.getString("departmentname");

        Position position = new Position(positionName);
        Department department = new Department(departmentId, departmentName, departmentAddress);
        return new Employee(id, email, name, privatePhoneNumber, workPhoneNumber, position, department);
    }
}