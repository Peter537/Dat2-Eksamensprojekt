package dat.backend.model.persistence;

import dat.backend.model.entities.Address;
import dat.backend.model.entities.Customer;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class CustomerMapper {

    static Optional<Customer> login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT * FROM customer WHERE email = ? AND password = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                return createCustomerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not login customer");
        }
    }

    static Optional<Customer> createCustomer(String email, String password, String name, ConnectionPool connectionPool) throws DatabaseException {
        String query = "INSERT INTO customer (email, password, name) VALUES (?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                statement.setString(3, name);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Could not create customer");
                }

                return getCustomerByEmail(email, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not create customer");
        }
    }

    static Optional<Customer> getCustomerById(int id, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT * FROM customer WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                return createCustomerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get customer by id");
        }
    }

    static Optional<Customer> getCustomerByEmail(String email, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT * FROM customer WHERE email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                return createCustomerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get customer by email");
        }
    }

    static void updatePassword(Customer customer, String newPassword, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE customer SET password = ? WHERE email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPassword);
                statement.setString(2, customer.getEmail());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update customer password");
        }
    }

    private static Optional<Customer> createCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }

        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        String personalPhoneNumber = resultSet.getString("personal_phone_number");
        Address address1 = null;
        Address address2 = null;
        Address address3 = null;
        return Optional.of(new Customer(id, email, password, name, personalPhoneNumber, address1, address2, address3));
    }
}