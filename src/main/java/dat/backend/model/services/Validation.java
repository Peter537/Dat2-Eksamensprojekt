package dat.backend.model.services;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.order.OrderStatus;
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
        validateAddress(address);
        validateWidth(width);
        validateLength(length);
        validateMinHeight(minHeight);
        validateRoof(roof);
        if (toolRoom.isPresent()) {
            validateToolRoom(toolRoom.get());
        }
        if (remarks.isPresent()) {
            validateRemarks(remarks.get());
        }
    }

    private static void validateRoof(Roof roof) throws ValidationException {
        if (roof == null) {
            throw new ValidationException("Invalid roof");
        }
    }

    public static void validateOrderStatus(OrderStatus newOrderStatus) throws ValidationException {
        if (newOrderStatus == null) {
            throw new ValidationException("Invalid orderStatus");
        }
    }

    public static void validateWidth(float width) throws ValidationException {
        if (width <= 0) {
            throw new ValidationException("Invalid width");
        }
    }

    public static void validateLength(float length) throws ValidationException {
        if (length <= 0) {
            throw new ValidationException("Invalid length");
        }
    }

    public static void validateMinHeight(float minHeight) throws ValidationException {
        if (minHeight <= 0) {
            throw new ValidationException("Invalid minHeight");
        }
    }

    public static void validateToolRoom(ToolRoom toolRoom) throws ValidationException {
        if (toolRoom == null) {
            throw new ValidationException("Invalid toolRoom");
        }

        validateWidth(toolRoom.getWidth());
        validateLength(toolRoom.getLength());
    }

    public static void validatePrice(Float aFloat) throws ValidationException {
        if (aFloat == null) {
            throw new ValidationException("Invalid price");
        }
    }

    public static void validateRemarks(String s) throws ValidationException {
        if (s.length() > 2048) {
            throw new ValidationException("Invalid remarks");
        }
    }

    public static void validateAddress(Address address) throws ValidationException {
        if (address == null) {
            throw new ValidationException("Invalid address");
        }
    }
}