package dat.backend.model.persistence;

import dat.backend.model.entities.Customer;

import java.util.Optional;

class CustomerMapper {

    static Optional<Customer> login(String email, String password, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static Optional<Customer> createCustomer(String email, String password, String name, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static Optional<Customer> getCustomerById(int id, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static Optional<Customer> getCustomerByEmail(String email, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static void updatePassword(Customer customer, String newPassword, ConnectionPool connectionPool) {
    }
}
