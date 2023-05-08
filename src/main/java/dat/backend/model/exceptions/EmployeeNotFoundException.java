package dat.backend.model.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException(String message) {
        super(message);
        Logger.getLogger("web").log(Level.INFO, message);
    }
}