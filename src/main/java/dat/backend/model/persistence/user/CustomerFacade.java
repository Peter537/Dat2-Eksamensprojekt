package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.AlreadyExistsException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.Validation;

import java.io.FileInputStream;

public class CustomerFacade {

    public static Customer login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        Validation.validateCustomer(email, password);
        return CustomerMapper.login(email, password, connectionPool);
    }

    public static Customer create(String email, String password, String name, ConnectionPool connectionPool) throws DatabaseException, ValidationException, AlreadyExistsException {
        Validation.validateCustomer(name, email, password);
        try {
            getCustomerByEmail(email, connectionPool);
            throw new AlreadyExistsException("Email already exists");
        } catch (NotFoundException e) {
            // Do nothing
        }

        return CustomerMapper.create(email, password, name, connectionPool);
    }

    public static Customer getCustomerByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return CustomerMapper.getCustomerByEmail(email, connectionPool);
    }
    public static byte[] getCustomerPicture(String email, ConnectionPool connectionPool) throws DatabaseException {
        return CustomerMapper.getCustomerPicture(email, connectionPool);
    }

    public static void updatePassword(Customer customer, String newPassword, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCustomer(customer.getName(), customer.getEmail(), newPassword);
        CustomerMapper.updatePassword(customer, newPassword, connectionPool);
    }

    public static void updateAddress(Customer customer, int addressNumber, String newStreetName, Zip zip, ConnectionPool connectionPool) throws DatabaseException {
        CustomerMapper.updateAddress(customer, addressNumber, newStreetName, zip, connectionPool);
    }

    public static void updateName(Customer customer, String newName, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateName(newName);
        CustomerMapper.updateName(customer, newName, connectionPool);
    }

    public static void updatePhoneNumber(Customer customer, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validatePhoneNumber(newPhoneNumber);
        CustomerMapper.updatePhoneNumber(customer, newPhoneNumber, connectionPool);
    }

    public static void updateProfilePicture(Customer customer, FileInputStream fileInputStream, ConnectionPool connectionPool) throws DatabaseException {
        CustomerMapper.updateProfilePicture(customer, fileInputStream, connectionPool);
    }
}