package dat.backend.model.persistence;

import dat.backend.model.entities.Customer;
import dat.backend.model.exceptions.DatabaseException;

import java.util.Optional;

public class CustomerFacade {

    public static Optional<Customer> login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        return CustomerMapper.login(email, password, connectionPool);
    }

    public static Optional<Customer> createCustomer(String email, String password, String name, ConnectionPool connectionPool) throws DatabaseException {
        return CustomerMapper.createCustomer(email, password, name, connectionPool);
    }

    public static Optional<Customer> getCustomerById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return CustomerMapper.getCustomerById(id, connectionPool);
    }

    public static Optional<Customer> getCustomerByEmail(String email, ConnectionPool connectionPool) throws DatabaseException {
        return CustomerMapper.getCustomerByEmail(email, connectionPool);
    }

    public static void updatePassword(Customer customer, String newPassword, ConnectionPool connectionPool) throws DatabaseException {
        CustomerMapper.updatePassword(customer, newPassword, connectionPool);
    }
}