package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Optional;

class CustomerMapper {

    /**
     * This method will log in a customer by email and password
     *
     * @param email          The email to search for
     * @param password       The password to search for
     * @param connectionPool Connection pool
     * @return The Customer object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the customer does not exist
     */
    static Customer login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM customerWithAddress WHERE email = ? AND password = ?";
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

    /**
     * This method will create a Customer from the arguments
     *
     * @param email          The email of the customer
     * @param password       The password of the customer
     * @param name           The name of the customer
     * @param connectionPool Connection pool
     * @return The Customer object
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static Customer create(String email, String password, String name, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will retrieve a Customer by email
     *
     * @param email          The email to search for
     * @param connectionPool Connection pool
     * @return The Customer object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the customer does not exist
     */
    static Customer getCustomerByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM customerWithAddress WHERE email = ?";
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

    static byte[] getCustomerPicture(String email, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT profilepicture FROM customer WHERE email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob("profilepicture");
                    if (blob != null) {
                        return blob.getBytes(1, (int) blob.length());
                    } else {
                        return null;
                    }
                } else {
                    throw new DatabaseException("Could not get customer picture");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get customer by email");
        }
    }

    /**
     * This method will update the password of a customer
     *
     * @param customer       The customer to update
     * @param newPassword    The new password
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
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

    /**
     * This method will update the address of a customer
     *
     * @param customer       The customer to update
     * @param addressNumber  The address number to update
     * @param newStreetName  The new street name
     * @param zip            The new zip
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
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

    /**
     * This method will update the name of a customer
     *
     * @param customer       The customer to update
     * @param newName        The new name
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateName(Customer customer, String newName, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the personal phone number of a customer
     *
     * @param customer       The customer to update
     * @param newPhoneNumber The new phone number
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updatePhoneNumber(Customer customer, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException {
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

    static void updateProfilePicture(Customer customer, FileInputStream fileInputStream, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE customer SET profilepicture = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setBlob(1, fileInputStream);
                statement.setInt(2, customer.getId());
                statement.executeUpdate();
                customer.setProfilePicture(getCustomerPicture(customer.getEmail(), connectionPool));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update customer profile picture");
        }
    }

    /**
     * This method will create a customer from a result set
     *
     * @param resultSet The result set
     * @return The customer
     * @throws SQLException      if an error occurs while communicating with the database
     * @throws NotFoundException if the customer does not exist
     */
    private static Customer createCustomerFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {
        if (!resultSet.next()) {
            throw new NotFoundException("Could not create customer from result set");
        }

        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        Optional<String> personalPhoneNumber = Optional.ofNullable(resultSet.getString("phonenumber"));
        Optional<Address> address1 = createCustomerAddressFromResultSet(1, resultSet);
        Optional<Address> address2 = createCustomerAddressFromResultSet(2, resultSet);
        Optional<Address> address3 = createCustomerAddressFromResultSet(3, resultSet);

        //Get profile picture
        Blob profilePicture = resultSet.getBlob("profilepicture");
        byte[] profilePictureBytes = null;

        if (profilePicture != null) {
            profilePictureBytes = profilePicture.getBytes(1, (int) profilePicture.length());
        }

        //Create customer
        Customer customer = new Customer(id, email, name, personalPhoneNumber, address1, address2, address3);

        //Set profile picture
        customer.setProfilePicture(profilePictureBytes);

        return customer;
    }

    /**
     * This method will create a customer address from a result set
     *
     * @param addressNumber The address number
     * @param resultSet     The result set
     * @return The customer address
     * @throws SQLException if an error occurs while communicating with the database
     */
    private static Optional<Address> createCustomerAddressFromResultSet(int addressNumber, ResultSet resultSet) throws SQLException {
        String address = resultSet.getString("address_" + addressNumber);
        int zipCode = resultSet.getInt("zipcode_" + addressNumber);
        String city = resultSet.getString("city_" + addressNumber);
        if (address == null || zipCode == 0) {
            return Optional.empty();
        }

        Zip zip = new Zip(zipCode, city);
        return Optional.of(new Address(address, zip));
    }
}