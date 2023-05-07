package dat.backend.model.persistence;

import dat.backend.model.entities.Customer;
import dat.backend.model.exceptions.CustomerAlreadyExistsException;
import dat.backend.model.exceptions.CustomerNotFoundException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;

public class CustomerFacade {

    public static Customer login(String email, String password, ConnectionPool connectionPool) throws DatabaseException, CustomerNotFoundException {
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
}