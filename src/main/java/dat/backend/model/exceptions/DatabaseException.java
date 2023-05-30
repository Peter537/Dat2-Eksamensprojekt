package dat.backend.model.exceptions;

import dat.backend.annotation.IgnoreCoverage;

import java.util.logging.Level;
import java.util.logging.Logger;

@IgnoreCoverage(reason = "We are not testing Exceptions")
public class DatabaseException extends Exception {

    public DatabaseException(String message) {
        super(message);
        Logger.getLogger("web").log(Level.SEVERE, message);
    }

    public DatabaseException(Exception ex, String message) {
        super(message);
        Logger.getLogger("web").log(Level.SEVERE, message, ex.getMessage());
    }
}