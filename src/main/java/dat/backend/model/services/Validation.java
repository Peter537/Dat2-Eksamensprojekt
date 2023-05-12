package dat.backend.model.services;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.user.*;
import dat.backend.model.exceptions.ValidationException;

import java.util.Optional;

public class Validation {

    public static void validateCustomer(Customer customer) throws ValidationException {
        if (customer == null) {
            throw new ValidationException("Invalid customer");
        }

        validateCustomer(customer.getName(), customer.getEmail(), customer.getPassword());
    }

    public static void validateCustomer(String email, String password) throws ValidationException {
        validateCustomer("Peter", email, password);
    }

    public static void validateCustomer(String name, String email, String password) throws ValidationException {
        if (!isValidName(name)) {
            throw new ValidationException("Invalid name");
        }

        if (!isValidCustomerEmail(email)) {
            throw new ValidationException("Invalid customer email");
        }

        if (!isValidPassword(password)) {
            throw new ValidationException("Invalid password");
        }
    }

    public static void validateEmployee(Employee employee) throws ValidationException {
        if (employee == null) {
            throw new ValidationException("Invalid employee");
        }

        validateEmployee(employee.getName(), employee.getEmail(), employee.getPassword());
    }

    public static void validateEmployee(String email, String password) throws ValidationException {
        validateEmployee("Peter", email, password);
    }

    public static void validateEmployee(String name, String email, String password) throws ValidationException {
        if (!isValidName(name)) {
            throw new ValidationException("Invalid name");
        }

        if (!isValidEmployeeEmail(email)) {
            throw new ValidationException("Invalid employee email");
        }

        if (!isValidPassword(password)) {
            throw new ValidationException("Invalid password");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) throws ValidationException {
        if (phoneNumber == null) {
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            throw new ValidationException("Invalid phone number");
        }
    }

    public static boolean isCustomerEmail(String email) {
        return isValidCustomerEmail(email);
    }

    public static boolean isEmployeeEmail(String email) {
        return isValidEmployeeEmail(email);
    }

    private static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        return password.length() >= 3 && password.length() <= 32;
    }

    private static boolean isValidName(String name) {
        return name != null;
    }

    private static boolean isValidEmployeeEmail(String email) {
        if (email == null) {
            return false;
        }

        return email.matches("^[A-Za-z0-9+_.-]+@johannesfog.dk$");
    }

    private static boolean isValidCustomerEmail(String email) {
        if (email == null) {
            return false;
        }

        if (email.matches("^[A-Za-z0-9+_.-]+@johannesfog.dk$")) {
            return false;
        }

        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9+_.-]+\\.)+[A-Za-z0-9+_.-]+$");
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[0-9]{8}$");
    }

    public static void validatePosition(Position newPosition) throws ValidationException {
        if (newPosition == null) {
            throw new ValidationException("Invalid position");
        }
    }

    public static void validateDepartment(Department newDepartment) throws ValidationException {
        if (newDepartment == null) {
            throw new ValidationException("Invalid department");
        }
    }

    public static void validateCreateCarportOrder(Customer customer, Address address, float width, float length, float minHeight, Roof roof, Optional<ToolRoom> toolRoom, Optional<String> remarks) throws ValidationException {
        validateCustomer(customer);
        if (address == null) {
            throw new ValidationException("Invalid address");
        }

        if (width <= 0) {
            throw new ValidationException("Invalid width");
        }

        if (length <= 0) {
            throw new ValidationException("Invalid length");
        }

        if (minHeight <= 0) {
            throw new ValidationException("Invalid minHeight");
        }

        if (roof == null) {
            throw new ValidationException("Invalid roof");
        }

        if (toolRoom.isPresent()) {
            if (toolRoom.get().getWidth() <= 0) {
                throw new ValidationException("Invalid toolRoom width");
            }

            if (toolRoom.get().getLength() <= 0) {
                throw new ValidationException("Invalid toolRoom length");
            }
        }

        if (remarks.isPresent()) {
            if (remarks.get().length() > 2048) {
                throw new ValidationException("Invalid remarks");
            }
        }
    }
}