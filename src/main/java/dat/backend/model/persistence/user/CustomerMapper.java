package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.*;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.Validation;

import java.sql.*;
import java.util.Optional;

class CustomerMapper {

    static Customer login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        Validation.validateCustomer(email, password);
        String query = "SELECT * FROM customer WHERE email = ? AND password = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                return createCustomerFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not login customer");
        }
    }

    static Customer createCustomer(String email, String password, String name, ConnectionPool connectionPool) throws DatabaseException, ValidationException, AlreadyExistsException {
        Validation.validateCustomer(name, email, password);
        try {
            getCustomerByEmail(email, connectionPool);
            throw new AlreadyExistsException("Email already exists");
        } catch (NotFoundException e) {
            // Do nothing
        }

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
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Could not create customer");
        }
    }

    static Customer getCustomerById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM customer WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                return createCustomerFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get customer by id");
        }
    }

    static Customer getCustomerByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM customer WHERE email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                return createCustomerFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get customer by email");
        }
    }

    static void updatePassword(Customer customer, String newPassword, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCustomer(customer.getName(), customer.getEmail(), newPassword);
        String query = "UPDATE customer SET password = ? WHERE email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPassword);
                statement.setString(2, customer.getEmail());
                statement.executeUpdate();
                customer.setPassword(newPassword);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update customer password");
        }
    }

    static void updateAddress(Customer customer, int addressNumber, String newStreetName, Zip zip, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE customer SET address_" + addressNumber + " = ?, zipcode_" + addressNumber + " = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (newStreetName == null || zip == null) {
                    statement.setNull(1, Types.NULL);
                    statement.setInt(2, Types.NULL);
                    statement.setInt(3, customer.getId());
                    statement.executeUpdate();
                    customer.setAddress(addressNumber, Optional.empty());
                } else {
                    statement.setString(1, newStreetName);
                    statement.setInt(2, zip.getZipCode());
                    statement.setInt(3, customer.getId());
                    statement.executeUpdate();
                    customer.setAddress(addressNumber, Optional.of(new Address(newStreetName, zip)));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update customer address");
        }
    }

    static void updateName(Customer customer, String newName, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCustomer(newName, customer.getEmail(), customer.getPassword());
        String query = "UPDATE customer SET name = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newName);
                statement.setInt(2, customer.getId());
                statement.executeUpdate();
                customer.setName(newName);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update customer name");
        }
    }

    static void updatePhoneNumber(Customer customer, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePhoneNumber(newPhoneNumber);
        String query = "UPDATE customer SET phonenumber = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newPhoneNumber);
                statement.setInt(2, customer.getId());
                statement.executeUpdate();
                customer.setPersonalPhoneNumber(Optional.ofNullable(newPhoneNumber));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update customer phone number");
        }
    }

    private static Customer createCustomerFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws DatabaseException, SQLException, NotFoundException {
        if (!resultSet.next()) {
            throw new NotFoundException("Could not create customer from result set");
        }

        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        Optional<String> personalPhoneNumber = Optional.ofNullable(resultSet.getString("phonenumber"));
        Optional<Address> address1 = createCustomerAddressFromResultSet(1, resultSet, connectionPool);
        Optional<Address> address2 = createCustomerAddressFromResultSet(2, resultSet, connectionPool);
        Optional<Address> address3 = createCustomerAddressFromResultSet(3, resultSet, connectionPool);
        return new Customer(id, email, name, password, personalPhoneNumber, address1, address2, address3);
    }

    private static Optional<Address> createCustomerAddressFromResultSet(int addressNumber, ResultSet resultSet, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        String address = resultSet.getString("address_" + addressNumber);
        int zipCode = resultSet.getInt("zipcode_" + addressNumber);
        if (address == null || zipCode == 0) {
            return Optional.empty();
        }

        try {
            Zip zip = ZipFacade.getZipByZipCode(zipCode, connectionPool);
            return Optional.of(new Address(address, zip));
        } catch (NotFoundException e) {
            throw new DatabaseException("Could not get zip by zip code");
        }
    }
}