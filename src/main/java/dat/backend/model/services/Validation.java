package dat.backend.model.services;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.*;
import dat.backend.model.exceptions.ValidationException;

import java.util.Optional;

public class Validation {

    public static void validateCustomer(Customer customer) throws ValidationException {
        if (customer == null) {
            throw new ValidationException("Invalid customer");
        }

        if (!isValidName(customer.getName())) {
            throw new ValidationException("Invalid name");
        }

        if (!isValidCustomerEmail(customer.getEmail())) {
            throw new ValidationException("Invalid customer email");
        }
    }

    public static void validateCustomer(String email, String password) throws ValidationException {
        if (!isValidCustomerEmail(email)) {
            throw new ValidationException("Invalid customer email");
        }

        if (!isValidPassword(password)) {
            throw new ValidationException("Invalid password");
        }
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

        if (!isValidName(employee.getName())) {
            throw new ValidationException("Invalid name");
        }

        if (!isValidEmployeeEmail(employee.getEmail())) {
            throw new ValidationException("Invalid employee email");
        }
    }

    public static void validateEmployee(String email, String password) throws ValidationException {
        if (!isValidEmployeeEmail(email)) {
            throw new ValidationException("Invalid employee email");
        }

        if (!isValidPassword(password)) {
            throw new ValidationException("Invalid password");
        }
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

    public static void validateName(String name) throws ValidationException {
        if (!isValidName(name)) {
            throw new ValidationException("Invalid name");
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

    private static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        return password.length() >= 3 && password.length() <= 32;
    }

    private static boolean isValidName(String name) {
        return name != null;
    }

    /**
     * We are using a Regex to check if the email is valid employee email.
     * The Regex is: ^[A-Za-z0-9+_.-]+@johannesfog.dk$
     * The first part before the @ means that the email can contain letters, numbers, +, _, . and -.
     * The second part after the @ means that the email must end with @johannesfog.dk
     *
     * @param email The email to check
     * @return True if the email is valid, false otherwise
     */
    public static boolean isValidEmployeeEmail(String email) {
        if (email == null) {
            return false;
        }

        return email.matches("^[A-Za-z0-9+_.-]+@johannesfog.dk$");
    }

    /**
     * We are using a Regex to check if the email is valid customer email.
     * The Regex is: ^[A-Za-z0-9+_.-]+@([A-Za-z0-9+_.-]+\\.)+[A-Za-z]+$
     * The first part before the @ means that the email can contain letters, numbers, +, _, . and -.
     * The second part after the @ means that the email must contain letters, numbers, +, _, . and -.
     * The third part after the . means that the email must end with letters.
     *
     * @param email The email to check
     * @return True if the email is valid, false otherwise
     */
    public static boolean isValidCustomerEmail(String email) {
        if (email == null) {
            return false;
        }

        if (email.matches("^[A-Za-z0-9+_.-]+@johannesfog.dk$")) {
            return false;
        }

        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9+_.-]+\\.)+[A-Za-z]+$");
    }

    /**
     * We are using a Regex to check if the phone number is valid.
     * The Regex is: ^[0-9]{8}$
     * This means that the phone number must contain 8 numbers.
     * If we were to add foreign phone numbers, we would have to change the Regex.
     *
     * @param phoneNumber The phone number to check
     * @return True if the phone number is valid, false otherwise
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[0-9]{8}$");
    }

    public static void validatePosition(Position position) throws ValidationException {
        if (position == null) {
            throw new ValidationException("Invalid position");
        }
    }

    public static void validateDepartment(Department department) throws ValidationException {
        if (department == null) {
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

    public static void validatePrice(Float f) throws ValidationException {
        if (f == null) {
            throw new ValidationException("Invalid price");
        }

        if (f < 0) {
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

    public static void validateCarportOrder(CarportOrder carportOrder) throws ValidationException {
        if (carportOrder == null) {
            throw new ValidationException("Invalid carportOrder");
        }
    }

    public static void validateAmount(int amount) throws ValidationException {
        if (amount <= 0) {
            throw new ValidationException("Invalid amount");
        }
    }

    public static void validateThickness(float thickness) throws ValidationException {
        if (thickness <= 0) {
            throw new ValidationException("Invalid thickness");
        }
    }
}