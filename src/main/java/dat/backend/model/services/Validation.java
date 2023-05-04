package dat.backend.model.services;

public class Validation {

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        if (password.length() < 3 || password.length() > 32) {
            return false;
        }

        return true;
    }

    public static boolean validateName(String name) {
        if (name == null) {
            return false;
        }

        return true;
    }
}