package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.CustomerAlreadyExistsException;
import dat.backend.model.exceptions.CustomerNotFoundException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;

public class CustomerFacade {

    public static Customer login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, CustomerNotFoundException, ValidationException {
        return CustomerMapper.login(email, password, connectionPool);
    }

    public static Customer createCustomer(String email, String password, String name, ConnectionPool connectionPool) throws DatabaseException, ValidationException, CustomerAlreadyExistsException {
        return CustomerMapper.createCustomer(email, password, name, connectionPool);
    }

    public static Customer getCustomerById(int id, ConnectionPool connectionPool) throws DatabaseException, CustomerNotFoundException {
        return CustomerMapper.getCustomerById(id, connectionPool);
    }

    public static Customer getCustomerByEmail(String email, ConnectionPool connectionPool) throws DatabaseException, CustomerNotFoundException {
        return CustomerMapper.getCustomerByEmail(email, connectionPool);
    }

    public static void updatePassword(Customer customer, String newPassword, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CustomerMapper.updatePassword(customer, newPassword, connectionPool);
    }

    public static void updateAddress(Customer customer, int addressNumber, String newStreetName, Zip zip, ConnectionPool connectionPool) throws DatabaseException {
        CustomerMapper.updateAddress(customer, addressNumber, newStreetName, zip, connectionPool);
    }

    public static void updateName(Customer customer, String newName, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CustomerMapper.updateName(customer, newName, connectionPool);
    }

    public static void updatePhoneNumber(Customer customer, String newPhoneNumber, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CustomerMapper.updatePhoneNumber(customer, newPhoneNumber, connectionPool);
    }
}