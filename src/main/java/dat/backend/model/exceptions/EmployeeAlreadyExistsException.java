package dat.backend.model.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeAlreadyExistsException extends Exception {

    public EmployeeAlreadyExistsException(String message) {
        super(message);
        Logger.getLogger("web").log(Level.INFO, message);
    }
}