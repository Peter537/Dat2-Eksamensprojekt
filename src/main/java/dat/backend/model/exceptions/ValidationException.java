package dat.backend.model.exceptions;

import dat.backend.annotation.IgnoreCoverage;

import java.util.logging.Level;
import java.util.logging.Logger;

@IgnoreCoverage(reason = "We are not testing Exceptions")
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
        Logger.getLogger("web").log(Level.INFO, message);
    }
}