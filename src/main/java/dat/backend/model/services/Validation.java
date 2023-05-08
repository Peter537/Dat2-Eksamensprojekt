package dat.backend.model.services;

import dat.backend.model.exceptions.ValidationException;

public class Validation {

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
}